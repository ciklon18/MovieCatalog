package com.example.moviecatalog.common.main.domain.usecase

import com.example.moviecatalog.common.main.domain.model.MoviesPagedListModel
import com.example.moviecatalog.common.main.domain.repository.MainRepository
import javax.inject.Inject

class GetPagedMoviesListUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(page: Int, token: String): Result<MoviesPagedListModel?> {

        return try{
            val result = mainRepository.getPagedMoviesList(page, token)
            if (result.isSuccess){
                Result.success(result.getOrNull())
            } else {
                Result.failure(result.exceptionOrNull() ?: Throwable("Empty data"))
            }
        } catch (e: Exception){
            Result.failure(Throwable(e.toString()))
        }

    }
}