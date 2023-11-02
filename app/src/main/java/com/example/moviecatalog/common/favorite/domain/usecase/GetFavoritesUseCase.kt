package com.example.moviecatalog.common.favorite.domain.usecase

import com.example.moviecatalog.common.favorite.domain.repository.FavoriteRepository
import com.example.moviecatalog.common.favorite.domain.model.FavoritesResponse
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun execute(token: String): Result<FavoritesResponse?> {
        return try {
            val result = favoriteRepository.getFavorites(token)
            if (result.isSuccess){
                val movieResponse = result.getOrNull()
                Result.success(movieResponse)
            } else {
                Result.failure(result.exceptionOrNull() ?: Throwable("Empty data"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.toString()))
        }
    }
}
