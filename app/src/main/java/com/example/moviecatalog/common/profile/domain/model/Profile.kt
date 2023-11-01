package com.example.moviecatalog.common.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val nickName: String,
    val email: String,
    val avatarLink: String,
    val name: String,
    val birthDate: String,
    val gender: Int
)
