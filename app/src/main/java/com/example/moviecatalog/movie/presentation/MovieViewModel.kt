package com.example.moviecatalog.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.main.data.mapper.toMovieUIState
import com.example.moviecatalog.common.main.domain.model.GenreModel
import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.model.ReviewModel
import com.example.moviecatalog.common.main.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUIState())
    val uiState: StateFlow<MovieUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    init {
        scope.launch(Dispatchers.Default) {
            val token = getTokenFromLocalStorageUseCase.execute()
            val movieDetails =
                _uiState.value.movieId?.let {
                    getMovieDetailsUseCase.execute(id = it, token = token).getOrNull()
                }
            if (movieDetails != null) {
                updateMovieUiState(movieDetails)
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

    private fun updateMovieUiState(movieDetails: MovieDetailsModel) {
        val movieUIState = movieDetails.toMovieUIState()
        _uiState.update { movieUIState }
    }

    private suspend fun updateUserReview() {
        val reviews = _uiState.value.reviews
        val userId = getProfileFromLocalStorageUseCase.execute().getOrNull()?.id

        val foundReview = reviews?.find { review ->
            review.author.userId == userId
        }

        if (foundReview != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    userReview = foundReview,
                    reviews = currentState.reviews?.filterNot { it == foundReview }
                )
            }
        }
    }

    fun onFavoriteButtonClicked() {
        scope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isFavorite = !currentState.isFavorite
                )
            }
        }
    }


    fun onAddReviewButtonClicked() {
        TODO("Not yet implemented")
    }

    fun onDeleteReviewClicked() {
        TODO("Not yet implemented")
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
    val userReview: ReviewModel? = null
)