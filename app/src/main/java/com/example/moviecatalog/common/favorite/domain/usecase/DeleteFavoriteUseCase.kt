package com.example.moviecatalog.common.favorite.domain.usecase

import com.example.moviecatalog.common.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun execute(token: String, id: String): Result<Unit>{
        return try {
            val result = favoriteRepository.deleteFavorite(token, id)
            if (result.isSuccess){
                Result.success(Unit)
            } else {
                Result.failure(result.exceptionOrNull() ?: Throwable("An error occurred while deleting a favorite"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.toString()))
        }
    }
}