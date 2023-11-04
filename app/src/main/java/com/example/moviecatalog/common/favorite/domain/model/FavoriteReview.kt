package com.example.moviecatalog.common.favorite.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteReview(
    val id: String,
    val rating: Int,
)
