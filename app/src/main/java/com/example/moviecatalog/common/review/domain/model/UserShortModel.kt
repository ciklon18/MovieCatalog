package com.example.moviecatalog.common.review.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserShortModel(
    val userId: String,
    val nickName: String?,
    val avatar: String?
)
