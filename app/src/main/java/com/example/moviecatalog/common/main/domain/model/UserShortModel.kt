package com.example.moviecatalog.common.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserShortModel(
    val userId: String,
    val nickName: String?,
    val avatar: String?
)
