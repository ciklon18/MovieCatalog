package com.example.moviecatalog.common.main.domain.usecase

import androidx.paging.PagingData
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.main.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieCardsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(): Flow<PagingData<UpdatedMovieElementModel>>{
        return movieRepository.getMovies()
    }
}