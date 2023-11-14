package com.example.moviecatalog.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.favorite.data.mapper.toFavoriteMovie
import com.example.moviecatalog.common.favorite.data.mapper.toFavoriteReview
import com.example.moviecatalog.common.favorite.domain.model.FavoriteMovie
import com.example.moviecatalog.common.favorite.domain.model.FavoriteReview
import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
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
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUIState())
    val uiState: StateFlow<FavoriteUIState> = _uiState.asStateFlow()
    
    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch(Dispatchers.Default) {
            val token = getTokenFromLocalStorageUseCase.execute()
            val result = getFavoritesUseCase.execute(token)
            val userId = getProfileFromLocalStorageUseCase.execute().getOrNull()?.id

            result.onSuccess { favoritesResponse ->
                val favoriteMovies = favoritesResponse?.movies?.map { movieResponse ->
                    val foundReview = getUserMovieReview(movieResponse.id, userId, token)
                    movieResponse.toFavoriteMovie(foundReview)
                } ?: emptyList()

                _uiState.update { currentState ->
                    currentState.copy(
                        movies = favoriteMovies,
                        profileId = userId,
                        isThereMovies = favoriteMovies.isNotEmpty()
                    )
                }
            }
        }
    }


    private suspend fun getUserMovieReview(
        movieId: String,
        userId: String?,
        token: String
    ): FavoriteReview? {
        val reviews = getMovieDetailsUseCase.execute(movieId, token)
            .getOrNull()?.reviews

        return reviews?.find { it.author?.userId == userId }?.toFavoriteReview()
    }

}


data class FavoriteUIState(
    val movies: List<FavoriteMovie> = emptyList(),
    val profileId: String? = null,
    val isThereMovies: Boolean = false
)