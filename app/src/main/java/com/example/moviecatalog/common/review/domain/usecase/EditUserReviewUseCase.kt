package com.example.moviecatalog.common.review.domain.usecase

import com.example.moviecatalog.common.review.domain.model.ReviewModel
import com.example.moviecatalog.common.review.domain.repository.ReviewRepository
import javax.inject.Inject

class EditUserReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun execute(movieId: String, reviewId: String, token: String, review: ReviewModel): Result<Unit>{
        return reviewRepository.editReview(movieId, reviewId, token, review)
    }
}