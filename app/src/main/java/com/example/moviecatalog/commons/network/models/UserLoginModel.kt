package com.example.moviecatalog.commons.network.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginModel(
    val username: String,
    val password: String
)
