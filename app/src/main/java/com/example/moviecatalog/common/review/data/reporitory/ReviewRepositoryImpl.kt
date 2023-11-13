package com.example.moviecatalog.common.review.data.reporitory

import com.example.moviecatalog.common.review.data.mapper.toReviewModifyModel
import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.review.data.service.ReviewApiService
import com.example.moviecatalog.common.review.domain.repository.ReviewRepository

class ReviewRepositoryImpl(private val reviewApiService: ReviewApiService) : ReviewRepository {
    override suspend fun postReview(movieId: String, token: String, review: ReviewModel): Result<Unit> {
        return try {
            val postReview = review.toReviewModifyModel()
            val response = reviewApiService.postReview(movieId, "Bearer $token", postReview)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editReview(movieId: String, reviewId: String, token: String, review: ReviewModel): Result<Unit> {
        return try {
            val editReview = review.toReviewModifyModel()
            val response = reviewApiService.editReview(movieId, reviewId, "Bearer $token", editReview)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteReview(movieId: String, reviewId: String, token: String): Result<Unit> {
        return try {
            val response = reviewApiService.deleteReview(movieId, reviewId, "Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}