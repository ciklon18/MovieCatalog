package com.example.moviecatalog.common.profile.di

import android.content.Context
import com.example.moviecatalog.common.profile.data.repository.ProfileRepositoryImpl
import com.example.moviecatalog.common.profile.data.service.ProfileApiService
import com.example.moviecatalog.common.profile.data.storage.ProfileStorageImpl
import com.example.moviecatalog.common.profile.domain.repository.ProfileRepository
import com.example.moviecatalog.common.profile.domain.storage.ProfileStorage
import com.example.moviecatalog.common.profile.domain.usecase.DeleteProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    @Singleton
    fun provideProfileStorage(@ApplicationContext context: Context): ProfileStorage {
        return ProfileStorageImpl(context)
    }

    @Provides
    fun provideApiImplementation(retrofit: Retrofit): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    fun provideProfileRepository(profileApiService: ProfileApiService): ProfileRepository {
        return ProfileRepositoryImpl(profileApiService)
    }

    @Provides
    fun provideGetProfileFromLocalStorageUseCase(profileStorage: ProfileStorage): GetProfileFromLocalStorageUseCase {
        return GetProfileFromLocalStorageUseCase(profileStorage = profileStorage)
    }

    @Provides
    fun provideSetProfileToLocalStorageUseCase(profileStorage: ProfileStorage): SetProfileToLocalStorageUseCase {
        return SetProfileToLocalStorageUseCase(profileStorage = profileStorage)
    }


    @Provides
    fun provideDeleteProfileFromLocalStorageUseCase(profileStorage: ProfileStorage): DeleteProfileFromLocalStorageUseCase {
        return DeleteProfileFromLocalStorageUseCase(profileStorage = profileStorage)
    }

}