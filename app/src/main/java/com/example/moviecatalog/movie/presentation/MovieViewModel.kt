package com.example.moviecatalog.movie.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.favorite.domain.usecase.DeleteFavoriteUseCase
import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.common.favorite.domain.usecase.PostFavoriteUseCase
import com.example.moviecatalog.common.main.data.mapper.toMovieUIState
import com.example.moviecatalog.common.main.domain.model.GenreModel
import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.review.data.mapper.toUserShortModel
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.review.domain.model.UserShortModel
import com.example.moviecatalog.common.review.domain.usecase.DeleteUserReviewUseCase
import com.example.moviecatalog.common.review.domain.usecase.EditUserReviewUseCase
import com.example.moviecatalog.common.review.domain.usecase.PostUserReviewUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val postFavoriteUseCase: PostFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val postUserReviewUseCase: PostUserReviewUseCase,
    private val editUserReviewUseCase: EditUserReviewUseCase,
    private val deleteUserReviewUseCase: DeleteUserReviewUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUIState())
    val uiState: StateFlow<MovieUIState> = _uiState.asStateFlow()


    init {
        _uiState.update { currentState -> currentState.copy(movieId = savedStateHandle["id"]) }
        initViewModel()
    }

    private fun initViewModel() = viewModelScope.launch(Dispatchers.Default) {
        val startDataTask = async { loadStartData() }
        val reviewAuthorTask = async { loadReviewAuthor() }

        startDataTask.await()
        reviewAuthorTask.await()

        if (startDataTask.isCompleted){
            val updateFavoriteStatusTask = async { updateFavoriteStatus() }
            updateFavoriteStatusTask.await()
        }


        if (reviewAuthorTask.isCompleted) {
            val userReviewTask = async { loadUserReview() }
            userReviewTask.await()
        }
    }


    private suspend fun loadStartData() {
        val token = getTokenFromLocalStorageUseCase.execute()
        _uiState.value.movieId?.let { movieId ->
            getMovieDetails(token, movieId)?.let { movieDetails ->
                _uiState.update { currentState ->
                    movieDetails.toMovieUIState().copy(
                        token = token,
                        movieId = currentState.movieId,
                        reviewAuthor = currentState.reviewAuthor
                    )
                }
            }
        }
    }

    private suspend fun loadReviewAuthor() {
        val userProfile = getProfileFromLocalStorageUseCase.execute().getOrNull()
        val reviewAuthor = userProfile?.toUserShortModel()
        _uiState.update { currentState ->
            currentState.copy(reviewAuthor = reviewAuthor)
        }
    }


    private fun loadUserReview() {
        val reviewAuthorId = _uiState.value.reviewAuthor?.userId

        _uiState.value.reviews?.firstOrNull { it.author?.userId == reviewAuthorId }
            ?.let { foundReview ->
                _uiState.update { currentState ->
                    currentState.copy(userReview = foundReview,
                        reviews = currentState.reviews?.filterNot { it == foundReview })
                }
            }
    }

    private suspend fun updateFavoriteStatus() {
        val token = _uiState.value.token
        val movieId = _uiState.value.movieId

        val favorites = token?.let { getFavoritesUseCase.execute(it).getOrNull() }
        val isFavorite = favorites?.movies?.firstOrNull { it.id == movieId }
        if (isFavorite != null) {
            _uiState.update { currentState ->
                currentState.copy(isFavorite = true)
            }
        }
    }



    fun onFavoriteButtonPressed() {
        viewModelScope.launch(Dispatchers.Default) {
            val isFavorite = _uiState.value.isFavorite

            _uiState.value.token?.let { token ->
                _uiState.value.movieId?.let { movieId ->
                    val result = if (!isFavorite) postFavoriteUseCase.execute(token, movieId)
                    else deleteFavoriteUseCase.execute(token, movieId)

                    if (result.isSuccess) {
                        _uiState.update { currentState ->
                            currentState.copy(isFavorite = !isFavorite)
                        }
                    }
                }
            }
        }
    }


    fun onAddReviewButtonPressed() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isReviewDialogVisible = true)
            }
        }
    }


    fun onEditReviewPressed() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isReviewDialogVisible = true)
            }
        }
    }

    fun onDismissReviewButtonPressed() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isReviewDialogVisible = false)
            }
        }
    }


    fun onSaveReviewButtonPressed(review: ReviewModel?) {
        viewModelScope.launch(Dispatchers.Default) {
            if (review == null) return@launch
            _uiState.update { currentState ->
                currentState.copy(isReviewDialogVisible = false)
            }
            updateReviewAuthorInfo(review).let { updatedReview ->
                if (updatedReview == null) return@launch

                if (_uiState.value.userReview == null) {
                    handlePostReviewPress(updatedReview)
                } else {
                    handleEditReviewPress(updatedReview)
                }
            }
        }
    }

    fun onDeleteReviewPressed() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = _uiState.value.movieId?.let { movieId ->
                _uiState.value.token?.let { token ->
                    _uiState.value.userReview?.let { userReview ->
                        userReview.id?.let {
                            deleteUserReviewUseCase.execute(
                                movieId = movieId, reviewId = userReview.id, token = token
                            )
                        }
                    }
                }
            }
            if (result != null && result.isSuccess) {
                _uiState.update { currentState ->
                    currentState.copy(userReview = null)
                }
            }
        }
    }


    private suspend fun handleEditReviewPress(review: ReviewModel) {
        val result = _uiState.value.movieId?.let { movieId ->
            _uiState.value.token?.let { token ->
                review.id?.let { id ->
                    editUserReviewUseCase.execute(
                        movieId = movieId, token = token, review = review, reviewId = id
                    )
                }

            }
        }
        if (result != null && result.isSuccess) {
            _uiState.update { currentState ->
                currentState.copy(userReview = review)
            }
        }
    }

    private suspend fun handlePostReviewPress(review: ReviewModel) {
        val result = _uiState.value.movieId?.let { movieId ->
            _uiState.value.token?.let { token ->
                postUserReviewUseCase.execute(
                    movieId = movieId, token = token, review = review
                )

            }
        }
        if (result != null && result.isSuccess) {
            viewModelScope.launch(Dispatchers.Default) {
                _uiState.update { currentState -> currentState.copy(userReview = null) }
                loadUpdatedReviews()
                updateUserReview()
            }
        }
    }

    private suspend fun loadUpdatedReviews() {
        _uiState.value.movieId?.let { movieId ->
            _uiState.value.token?.let { token ->
                getMovieDetails(token, movieId)?.let { movieDetails ->
                    _uiState.update { currentState ->
                        currentState.copy(reviews = movieDetails.reviews)
                    }
                }
            }
        }
    }

    private fun updateUserReview() {
        val reviewAuthorId = _uiState.value.reviewAuthor?.userId
        _uiState.value.reviews?.firstOrNull { it.author?.userId == reviewAuthorId }
            ?.let { foundReview ->
                _uiState.update { currentState ->
                    currentState.copy(userReview = foundReview,
                        reviews = currentState.reviews?.filterNot { it == foundReview })
                }
            }
    }


    private fun updateReviewAuthorInfo(review: ReviewModel): ReviewModel? {
        return _uiState.value.reviewAuthor?.let { review.copy(author = it) }
    }


    private suspend fun getMovieDetails(token: String, movieId: String): MovieDetailsModel? {
        return getMovieDetailsUseCase.execute(id = movieId, token = token).getOrNull()
    }
}


data class MovieUIState(
    val movieId: String? = null,
    val name: String? = null,
    val poster: String? = null,
    val year: Int? = null,
    val country: String? = null,
    val genres: List<GenreModel>? = emptyList(),
    val reviews: List<ReviewModel>? = emptyList(),
    val time: Int? = null,
    val tagline: String? = null,
    val description: String? = null,
    val director: String? = null,
    val budget: Int? = null,
    val fees: Int? = null,
    val ageLimit: Int? = null,
    val isFavorite: Boolean = false,
    val isVisibleActionButtons: Boolean = false,
    val isReviewDialogVisible: Boolean = false,
    val token: String? = null,
    val reviewAuthor: UserShortModel? = null,
    val userReview: ReviewModel? = null
)