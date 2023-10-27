package com.example.moviecatalog.commons.storage.di

import android.content.Context
import com.example.moviecatalog.commons.storage.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun ProvideUserPreferencesRepository(@ApplicationContext context: Context) : DataStoreRepository{
        return DataStoreRepository(context)
    }

}