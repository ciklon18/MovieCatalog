package com.example.moviecatalog.common.review.data.service

import com.example.moviecatalog.common.review.domain.model.ReviewModifyModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApiService {
    @POST("api/movie/{movieId}/review/add")
    suspend fun postReview(
        @Path("movieId") movieId: String,
        @Header("Authorization") token: String,
        @Body review: ReviewModifyModel
    ): Response<Unit>

    @PUT("api/movie/{movieId}/review/{id}/edit")
    suspend fun editReview(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String,
        @Body review: ReviewModifyModel
    ): Response<Unit>

    @DELETE("api/movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(
        @Path("movieId") movieId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String
    ): Response<Unit>
}