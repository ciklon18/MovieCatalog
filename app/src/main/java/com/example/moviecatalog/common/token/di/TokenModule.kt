package com.example.moviecatalog.common.token.di

import android.content.Context
import com.example.moviecatalog.common.token.JwtTokenHelper
import com.example.moviecatalog.common.token.data.storage.TokenStorageImpl
import com.example.moviecatalog.common.token.domain.storage.TokenStorage
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.IsTokenExpiredUseCase
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenModule {

    @Provides
    @Singleton
    fun provideTokenStorage(@ApplicationContext context: Context): TokenStorage {
        return TokenStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideJwtTokenHelper(): JwtTokenHelper{
        return JwtTokenHelper()
    }

    @Provides
    fun provideGetTokenFromLocalStorageUseCase(tokenStorage: TokenStorage): GetTokenFromLocalStorageUseCase {
        return GetTokenFromLocalStorageUseCase(tokenStorage = tokenStorage)
    }

    @Provides
    fun provideSetTokenToLocalStorageUseCase(tokenStorage: TokenStorage): SetTokenToLocalStorageUseCase {
        return SetTokenToLocalStorageUseCase(tokenStorage = tokenStorage)
    }



    @Provides
    fun provideIsTokenExpiredUseCase(jwtTokenHelper: JwtTokenHelper): IsTokenExpiredUseCase{
        return IsTokenExpiredUseCase(jwtTokenHelper)
    }

}