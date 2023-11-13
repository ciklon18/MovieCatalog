package com.example.moviecatalog.common.review.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModifyModel(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)
