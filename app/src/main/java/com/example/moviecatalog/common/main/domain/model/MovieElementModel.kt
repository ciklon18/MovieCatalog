package com.example.moviecatalog.common.main.domain.model

import com.example.moviecatalog.common.review.domain.model.ReviewShortModel
import kotlinx.serialization.Serializable

@Serializable
data class MovieElementModel(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<GenreModel>?,
    val reviews: List<ReviewShortModel>?
)
