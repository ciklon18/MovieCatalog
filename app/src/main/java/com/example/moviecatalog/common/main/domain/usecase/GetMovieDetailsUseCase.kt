package com.example.moviecatalog.common.main.domain.usecase

import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.repository.MainRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(id: String, token: String): Result<MovieDetailsModel?>{
        return  try {
            val result = mainRepository.getMovieDetails(id, token)
            if (result.isSuccess){
                val movieDetailsModel = result.getOrNull()
                Result.success(movieDetailsModel)
            } else{
                Result.failure(result.exceptionOrNull() ?: Throwable("Empty data"))
            }
        } catch (e: Exception){
            Result.failure(Throwable(e.toString()))
        }
    }
}