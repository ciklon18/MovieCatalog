package com.example.moviecatalog.common.review.domain.usecase

import com.example.moviecatalog.common.review.domain.repository.ReviewRepository
import javax.inject.Inject

class DeleteUserReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun execute(movieId: String, reviewId: String, token: String): Result<Unit>{
        return reviewRepository.deleteReview(movieId = movieId, reviewId = reviewId, token = token)
    }
}