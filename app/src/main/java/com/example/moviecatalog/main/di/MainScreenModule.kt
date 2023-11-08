package com.example.moviecatalog.main.di

import com.example.moviecatalog.common.main.domain.usecase.GetMovieCardsUseCase
import com.example.moviecatalog.main.presentation.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainScreenModule {
    @Provides
    fun provideMainViewModel(
        getMovieCardsUseCase: GetMovieCardsUseCase,
    ): MainViewModel {
        return MainViewModel(
            getMovieCardsUseCase = getMovieCardsUseCase,
        )
    }
}