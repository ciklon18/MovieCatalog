package com.example.moviecatalog.common.favorite.data.mapper

import com.example.moviecatalog.common.favorite.domain.model.FavoriteMovie
import com.example.moviecatalog.common.favorite.domain.model.FavoriteReview
import com.example.moviecatalog.common.favorite.domain.model.MovieResponse
import com.example.moviecatalog.common.main.domain.model.ReviewModel


fun MovieResponse.toFavoriteMovie(userReview: FavoriteReview?): FavoriteMovie {
    return FavoriteMovie(
        id = this.id,
        name = this.name,
        poster = this.poster,
        year = this.year,
        country = this.country,
        genres = this.genres,
        reviews = this.reviews,
        userReview = userReview
    )
}

fun ReviewModel.toFavoriteReview(): FavoriteReview{
    return FavoriteReview(
        id = this.id,
        rating = this.rating
    )
}