package com.example.moviecatalog.common.auth.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class UserRegisterModel(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate : String,
    val gender: Int
)
