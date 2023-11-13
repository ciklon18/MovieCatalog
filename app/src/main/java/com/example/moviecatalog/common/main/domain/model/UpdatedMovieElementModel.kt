package com.example.moviecatalog.common.main.domain.model

import com.example.moviecatalog.common.review.domain.model.ReviewModel
import kotlinx.serialization.Serializable

@Serializable
data class UpdatedMovieElementModel(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int?,
    val country: String?,
    val genres: List<GenreModel>?,
    val reviews: List<ReviewShortModel>?,
    val userReview: ReviewModel?
)
