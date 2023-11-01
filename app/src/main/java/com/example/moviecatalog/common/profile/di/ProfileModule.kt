package com.example.moviecatalog.common.profile.di

import com.example.moviecatalog.common.profile.data.repository.ProfileRepositoryImpl
import com.example.moviecatalog.common.profile.data.service.ProfileApiService
import com.example.moviecatalog.common.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {
    @Provides
    fun provideApiImplementation(retrofit: Retrofit): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    fun provideProfileRepository(profileApiService: ProfileApiService): ProfileRepository {
        return ProfileRepositoryImpl(profileApiService)
    }
}