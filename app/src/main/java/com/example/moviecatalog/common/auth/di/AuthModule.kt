package com.example.moviecatalog.common.auth.di

import com.example.moviecatalog.common.auth.data.repository.AuthRepositoryImpl
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.auth.data.service.AuthApiService
import com.example.moviecatalog.common.auth.domain.usecase.LogoutUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideApiImplementation(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    fun provideAuthRepository(authApiService: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }

    @Provides
    fun provideLogoutUserUseCase(authRepository: AuthRepository): LogoutUserUseCase {
        return LogoutUserUseCase(authRepository = authRepository)
    }
}