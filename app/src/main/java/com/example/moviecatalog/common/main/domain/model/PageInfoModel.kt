package com.example.moviecatalog.common.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoModel(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)
