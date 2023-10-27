package com.example.moviecatalog.common.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginModel(
    val username: String,
    val password: String
)
