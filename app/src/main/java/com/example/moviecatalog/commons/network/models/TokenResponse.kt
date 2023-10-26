package com.example.moviecatalog.commons.network.models

import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    val token: String
)
