package com.example.moviecatalog.movie.di

import com.example.moviecatalog.common.main.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.movie.presentation.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MovieModule {
    @Provides
    fun provideMovieViewModel(
        getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
        getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
        getMovieDetailsUseCase: GetMovieDetailsUseCase
    ): MovieViewModel {
        return MovieViewModel(
            getProfileFromLocalStorageUseCase = getProfileFromLocalStorageUseCase,
            getTokenFromLocalStorageUseCase = getTokenFromLocalStorageUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase,
        )
    }
}