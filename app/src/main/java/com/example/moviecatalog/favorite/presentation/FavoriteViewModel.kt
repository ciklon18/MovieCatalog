package com.example.moviecatalog.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.favorite.domain.model.MovieResponse
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
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUIState())
    val uiState: StateFlow<FavoriteUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    init {
        loadMovies()
    }

    private fun loadMovies() {
        scope.launch(Dispatchers.Default){
            val token = getTokenFromLocalStorageUseCase.execute()
            val result = getFavoritesUseCase.execute(token)

            result.onSuccess { favoritesResponse ->
                if (favoritesResponse?.movies?.isNotEmpty() == true) {
                    _uiState.update { currentState ->
                        currentState.copy(movies = favoritesResponse.movies, isThereMovies = true)
                    }
                }
            }
        }
    }
}


data class FavoriteUIState(
    val movies: List<MovieResponse> = emptyList(),
    val isThereMovies: Boolean = false
)