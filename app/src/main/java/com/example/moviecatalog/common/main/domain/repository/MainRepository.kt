package com.example.moviecatalog.common.main.domain.repository

import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.model.MoviesPagedListModel

interface MainRepository {
    suspend fun getPagedMoviesList(page: Int, token: String): Result<MoviesPagedListModel?>
    suspend fun getMovieDetails(id: String, token: String): Result<MovieDetailsModel?>
}