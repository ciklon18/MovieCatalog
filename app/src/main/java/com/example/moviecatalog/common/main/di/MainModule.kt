package com.example.moviecatalog.common.main.di

import com.example.moviecatalog.common.main.data.repository.MainRepositoryImpl
import com.example.moviecatalog.common.main.data.repository.MovieRepositoryImpl
import com.example.moviecatalog.common.main.data.service.MainApiService
import com.example.moviecatalog.common.main.domain.repository.MainRepository
import com.example.moviecatalog.common.main.domain.repository.MovieRepository
import com.example.moviecatalog.common.main.domain.usecase.GetUpdatedMoviesPagedListUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    fun provideApiImplementation(retrofit: Retrofit): MainApiService {
        return retrofit.create(MainApiService::class.java)
    }

    @Provides
    fun provideMainRepository(mainApiService: MainApiService): MainRepository {
        return MainRepositoryImpl(mainApiService)
    }

    @Provides
    fun provideMovieRepository(
        getUpdatedMoviesPagedListUseCase: GetUpdatedMoviesPagedListUseCase,
        getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
    ): MovieRepository{
        return MovieRepositoryImpl(
            getUpdatedMoviesPagedListUseCase = getUpdatedMoviesPagedListUseCase,
            getTokenFromLocalStorageUseCase = getTokenFromLocalStorageUseCase
        )
    }
}