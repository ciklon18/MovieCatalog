package com.example.moviecatalog.common.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponse(
    val token: String,
    val message : String
)
