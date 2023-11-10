package com.example.moviecatalog.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.favorite.domain.usecase.DeleteFavoriteUseCase
import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.common.favorite.domain.usecase.PostFavoriteUseCase
import com.example.moviecatalog.common.main.data.mapper.toMovieUIState
import com.example.moviecatalog.common.main.domain.model.GenreModel
import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.model.ReviewModel
import com.example.moviecatalog.common.main.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
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
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUIState())
    val uiState: StateFlow<MovieUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    init {
        scope.launch(Dispatchers.Default) {
            val startDataTask = async { loadStartData() }
            val userReviewTask = async { loadUserReview() }
            val updateFavoriteStatusTask = async { updateFavoriteStatus() }

            startDataTask.await()
            userReviewTask.await()
            updateFavoriteStatusTask.await()
        }

    }

    private suspend fun loadStartData() {
        val token = getTokenFromLocalStorageUseCase.execute()
        _uiState.value.movieId?.let { movieId ->
            getMovieDetails(token, movieId)?.let { movieDetails ->
                _uiState.update {
                    movieDetails.toMovieUIState().copy(token = token, movieId = it.movieId)
                }
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

    fun setMovieId(movieId: String?) {
        if (movieId != null) {
            _uiState.update { currentState ->
                currentState.copy(movieId = movieId)
            }
        }

    }


    private suspend fun loadUserReview() {
        val reviews = _uiState.value.reviews
        val userId = getUserId()

        reviews?.find { review -> review.author.userId == userId }.let { foundReview ->
            _uiState.update { currentState ->
                currentState.copy(
                    userReview = foundReview,
                    reviews = currentState.reviews?.filterNot { it == foundReview }
                )
            }
        }
    }


    fun onFavoriteButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            val isFavorite = _uiState.value.isFavorite

            _uiState.value.token?.let { token ->
                _uiState.value.movieId?.let { movieId ->
                    val result = if (!isFavorite)
                        postFavoriteUseCase.execute(token, movieId)
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


    fun onAddReviewButtonClicked() {
        TODO("Not yet implemented")
    }

    fun onDeleteReviewClicked() {
        TODO("Not yet implemented")
    }

    private suspend fun getMovieDetails(token: String, movieId: String): MovieDetailsModel? {
        return getMovieDetailsUseCase.execute(id = movieId, token = token).getOrNull()
    }

    private suspend fun getUserId(): String? {
        return getProfileFromLocalStorageUseCase.execute().getOrNull()?.id
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
    val token: String? = null,
    val userReview: ReviewModel? = null
)