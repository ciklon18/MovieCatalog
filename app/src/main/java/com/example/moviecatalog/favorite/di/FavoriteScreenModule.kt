package com.example.moviecatalog.favorite.di

import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
import com.example.moviecatalog.common.main.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.favorite.presentation.FavoriteViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FavoriteScreenModule {
    @Provides
    fun provideFavoriteViewModel(
        getFavoritesUseCase: GetFavoritesUseCase,
        getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
        getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
        getMovieDetailsUseCase: GetMovieDetailsUseCase
    ): FavoriteViewModel
    {
        return FavoriteViewModel(
            getFavoritesUseCase = getFavoritesUseCase,
            getTokenFromLocalStorageUseCase = getTokenFromLocalStorageUseCase,
            getProfileFromLocalStorageUseCase = getProfileFromLocalStorageUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase
        )

    }
}