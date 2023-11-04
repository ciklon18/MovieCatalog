package com.example.moviecatalog.common.favorite.di

import com.example.moviecatalog.common.favorite.data.repository.FavoriteRepositoryImpl
import com.example.moviecatalog.common.favorite.data.service.FavoriteApiService
import com.example.moviecatalog.common.favorite.domain.repository.FavoriteRepository
import com.example.moviecatalog.common.favorite.domain.usecase.GetFavoritesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class FavoriteModule {
    @Provides
    fun provideApiImplementation(retrofit: Retrofit): FavoriteApiService {
        return retrofit.create(FavoriteApiService::class.java)
    }

    @Provides
    fun provideFavoriteRepository(favoriteApiService: FavoriteApiService): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteApiService)
    }
    @Provides
    fun provideGetFavoritesUseCase(favoriteRepository: FavoriteRepository): GetFavoritesUseCase{
        return GetFavoritesUseCase(favoriteRepository)
    }
}