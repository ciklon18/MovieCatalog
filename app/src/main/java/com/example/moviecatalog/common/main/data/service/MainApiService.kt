package com.example.moviecatalog.common.main.data.service

import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.model.MoviesPagedListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MainApiService {
    @GET("api/movies/{page}")
    suspend fun getPagedListMovies(
        @Path("page") page: Int,
        @Header("Authorization") token: String
    ): Response<MoviesPagedListModel>

    @GET("api/movies/details/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<MovieDetailsModel>
}




