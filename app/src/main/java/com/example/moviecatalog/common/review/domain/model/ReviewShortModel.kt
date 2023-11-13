package com.example.moviecatalog.common.review.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewShortModel(
    val id: String,
    val rating: Int
)