package com.example.moviecatalog.common.main.domain.repository

import androidx.paging.PagingData
import com.example.moviecatalog.common.main.domain.model.UpdatedMovieElementModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<PagingData<UpdatedMovieElementModel>>
}