package com.example.moviecatalog.common.favorite.domain.repository

import com.example.moviecatalog.common.favorite.domain.model.FavoritesResponse

interface FavoriteRepository {
    suspend fun getFavorites(token: String): Result<FavoritesResponse?>
    suspend fun postFavorite(token: String, id: String): Result<Unit>
    suspend fun deleteFavorite(token: String, id: String): Result<Unit>
}