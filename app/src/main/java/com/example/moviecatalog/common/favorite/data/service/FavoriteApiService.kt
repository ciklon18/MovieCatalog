package com.example.moviecatalog.common.favorite.data.service

import com.example.moviecatalog.common.favorite.domain.model.FavoritesResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApiService {
    @GET("api/favorites")
    suspend fun getFavorites(@Header("Authorization") token: String): Response<FavoritesResponse>

    @POST("api/favorites/{id}/add")
    suspend fun postFavorite(
        @Header("Authorization") token: String,
        @Path("id") movieId: String
    ): Response<Unit>

    @DELETE("api/favorites/{id}/delete")
    suspend fun deleteFavorite(
        @Header("Authorization") token: String,
        @Path("id") movieId: String
    ): Response<Unit>
}