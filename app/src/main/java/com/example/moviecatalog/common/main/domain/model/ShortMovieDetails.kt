package com.example.moviecatalog.common.main.domain.model

data class ShortMovieDetails(
    val year: Int?,
    val country: String?,
    val tagline: String?,
    val director: String?,
    val budget: Int?,
    val fees: Int?,
    val ageLimit: Int?,
    val time: Int?
)