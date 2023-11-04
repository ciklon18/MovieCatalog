package com.example.moviecatalog.common.favorite.data.repository

import com.example.moviecatalog.common.favorite.data.service.FavoriteApiService
import com.example.moviecatalog.common.favorite.domain.repository.FavoriteRepository
import com.example.moviecatalog.common.favorite.domain.model.FavoritesResponse

class FavoriteRepositoryImpl(private val favoriteApiService: FavoriteApiService) : FavoriteRepository {
    override suspend fun getFavorites(token: String): Result<FavoritesResponse?> {
        return try {
            val response = favoriteApiService.getFavorites("Bearer $token")
            if (response.isSuccessful) {
                val profile = response.body()
                Result.success(profile)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postFavorite(token: String, id: String): Result<Unit> {
        return try {
            val response = favoriteApiService.postFavorite(token = "Bearer $token", movieId = id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to add favorite"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteFavorite(token: String, id: String): Result<Unit> {
        return try {
            val response = favoriteApiService.deleteFavorite(token = "Bearer $token", movieId = id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete favorite"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}