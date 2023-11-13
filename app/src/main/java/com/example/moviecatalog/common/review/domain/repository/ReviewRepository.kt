package com.example.moviecatalog.common.review.domain.repository

import com.example.moviecatalog.common.review.domain.model.ReviewModel


interface ReviewRepository {
    suspend fun postReview(movieId: String, token: String, review: ReviewModel): Result<Unit>
    suspend fun editReview(
        movieId: String,
        reviewId: String,
        token: String,
        review: ReviewModel
    ): Result<Unit>

    suspend fun deleteReview(movieId: String, reviewId: String, token: String): Result<Unit>

}