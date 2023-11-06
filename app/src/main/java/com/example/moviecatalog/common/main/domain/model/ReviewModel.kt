package com.example.moviecatalog.common.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModel(
    val id: String,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModel
)
