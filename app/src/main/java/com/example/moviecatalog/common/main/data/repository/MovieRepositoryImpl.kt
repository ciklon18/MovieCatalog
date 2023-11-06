package com.example.moviecatalog.common.main.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecatalog.common.main.data.paging.MoviesPagingSource
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import com.example.moviecatalog.common.main.domain.repository.MovieRepository
import com.example.moviecatalog.common.main.domain.usecase.GetUpdatedMoviesPagedListUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val getUpdatedMoviesPagedListUseCase: GetUpdatedMoviesPagedListUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
) : MovieRepository {
    override suspend fun getMovies(): Flow<PagingData<UpdatedMovieElementModel>> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = {
                MoviesPagingSource(
                    getUpdatedMoviesPagedListUseCase,
                    getTokenFromLocalStorageUseCase
                )
            }
        ).flow
    }
}