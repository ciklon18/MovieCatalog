package com.example.moviecatalog.common.main.domain.usecase

import com.example.moviecatalog.common.main.data.mapper.toUpdatedMovieElementModel
import com.example.moviecatalog.common.main.domain.model.MovieElementModel
import com.example.moviecatalog.common.main.domain.model.PageInfoModel
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.main.domain.model.UpdatedMoviesPagedListModel
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import javax.inject.Inject

class GetUpdatedMoviesPagedListUseCase @Inject constructor(
    private val getPagedMoviesListUseCase: GetPagedMoviesListUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) {
    suspend fun execute(page: Int, token: String): Result<UpdatedMoviesPagedListModel?> {
        try {
            val userId = getProfileId(token)
            val moviesResult = getPagedMoviesListUseCase.execute(page, token)

            return moviesResult.fold(
                onSuccess = { moviesPagedListModel ->
                    val updatedMovieElementsList =
                        updateMoviesWithDetails(moviesPagedListModel?.movies, token, userId)
                    val pageInfo = moviesResult.getOrNull()?.pageInfo
                    if (updatedMovieElementsList != null) {
                        getResultWithMoviesResult(pageInfo, updatedMovieElementsList)
                    } else {
                        Result.failure(moviesResult.exceptionOrNull() ?: Throwable("Data empty"))
                    }
                },
                onFailure = {
                    Result.failure(it)
                }
            )


        } catch (e: Exception) {
            return Result.failure(Throwable(e.toString()))
        }
    }

    private suspend fun updateMoviesWithDetails(
        movies: List<MovieElementModel?>?,
        token: String,
        userId: String?
    ): List<UpdatedMovieElementModel>? {
        if (movies == null) return null
        if (userId == null) return movies.mapNotNull { it?.toUpdatedMovieElementModel(null) }


        return movies.mapNotNull { movie ->
            getMovieDetailsUseCase.execute(movie!!.id, token).fold(
                onSuccess = { movieDetails ->
                    val userReview =
                        movieDetails?.reviews?.firstOrNull { it.author?.userId == userId }
                    movieDetails?.toUpdatedMovieElementModel(userReview)
                        ?: movie.toUpdatedMovieElementModel(userReview)

                },
                onFailure = { movie.toUpdatedMovieElementModel(null) }
            )
        }

    }

    private suspend fun getProfileId(token: String): String? {
        return getProfileFromLocalStorageUseCase.execute()
            .getOrElse { getProfileUseCase.execute(token).getOrNull() }
            ?.id
    }

    private fun getResultWithMoviesResult(
        pageInfo: PageInfoModel?, updatedMovieElementsList: List<UpdatedMovieElementModel>
    ): Result<UpdatedMoviesPagedListModel?> {
        return if (pageInfo != null) {
            Result.success(
                UpdatedMoviesPagedListModel(
                    pageInfo = pageInfo, movies = updatedMovieElementsList
                )
            )
        } else {
            Result.failure(Throwable("Page info is missing"))
        }
    }
}