package com.example.moviecatalog.common.auth.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    val token: String
)
