package com.example.moviecatalog.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.main.domain.usecase.GetMovieCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieCardsUseCase: GetMovieCardsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    init {
        loadPage()
    }

    private fun loadPage() {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                try {
                    val movies = getMovieCardsUseCase.execute()
                    currentState.copy(
                        movies = movies
                    )
                } catch (e: Exception) {
                    currentState.copy(
                        movies = emptyFlow()
                    )

                }
            }
        }
    }
}

data class MainUiState(
    val movies: Flow<PagingData<UpdatedMovieElementModel>> = emptyFlow(),
)