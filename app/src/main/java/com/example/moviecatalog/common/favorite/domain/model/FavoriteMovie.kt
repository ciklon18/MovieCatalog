package com.example.moviecatalog.common.favorite.domain.model

data class FavoriteMovie(
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<Genre>,
    val reviews: List<FavoriteReview>,
    val userReview: FavoriteReview?
)
