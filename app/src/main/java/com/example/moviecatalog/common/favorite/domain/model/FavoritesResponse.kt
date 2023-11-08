package com.example.moviecatalog.common.favorite.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoritesResponse(
    val movies: List<MovieResponse>
)
