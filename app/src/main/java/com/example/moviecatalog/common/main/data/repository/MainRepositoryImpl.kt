package com.example.moviecatalog.common.main.data.repository

import com.example.moviecatalog.common.main.data.service.MainApiService
import com.example.moviecatalog.common.main.domain.model.MovieDetailsModel
import com.example.moviecatalog.common.main.domain.model.MoviesPagedListModel
import com.example.moviecatalog.common.main.domain.repository.MainRepository

class MainRepositoryImpl(private val mainApiService: MainApiService) : MainRepository {
    override suspend fun getPagedMoviesList(page: Int, token: String): Result<MoviesPagedListModel?> {
        return try {
            val response = mainApiService.getPagedListMovies(page, token)
            if (response.isSuccessful) {
                val profile = response.body()
                Result.success(profile)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(id: String, token: String): Result<MovieDetailsModel?> {
        return try {
            val response = mainApiService.getMovieDetails(id, token)
            if (response.isSuccessful) {
                val movies = response.body()
                Result.success(movies)
            } else {
                Result.failure(Exception("Response body is null or empty"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}