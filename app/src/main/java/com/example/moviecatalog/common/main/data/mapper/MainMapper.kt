package com.example.moviecatalog.common.main.data.mapper

import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.model.MovieElementModel
import com.example.moviecatalog.common.main.domain.model.ReviewModel
import com.example.moviecatalog.common.main.domain.model.ReviewShortModel
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel

fun MovieDetailsModel.toUpdatedMovieElementModel(userReview: ReviewModel?): UpdatedMovieElementModel {
    return UpdatedMovieElementModel(
        id = this.id,
        name = this.name,
        poster = this.poster,
        year = this.year,
        country = this.country,
        genres = this.genres,
        reviews = this.reviews?.map { reviewModel -> reviewModel.toReviewShortModel() },
        userReview = userReview
    )
}


fun MovieElementModel.toUpdatedMovieElementModel(userReviewModel: ReviewModel?): UpdatedMovieElementModel {

    return UpdatedMovieElementModel(
        id = this.id,
        name = this.name,
        poster = this.poster,
        year = this.year,
        country = this.country,
        genres = this.genres,
        reviews = this.reviews,
        userReview = userReviewModel
    )
}

fun ReviewModel.toReviewShortModel(): ReviewShortModel {
    return ReviewShortModel(
        id = this.id,
        rating = this.rating
    )
}