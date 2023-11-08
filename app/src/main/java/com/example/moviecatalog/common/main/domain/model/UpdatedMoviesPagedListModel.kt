package com.example.moviecatalog.common.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdatedMoviesPagedListModel(
    val pageInfo: PageInfoModel,
    val movies: List<UpdatedMovieElementModel>?
)
