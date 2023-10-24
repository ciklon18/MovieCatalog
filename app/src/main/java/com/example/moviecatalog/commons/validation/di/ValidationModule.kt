package com.example.moviecatalog.commons.validation.di

import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ValidationModule {

    @Provides
    fun provideValidateLoginUseCase() : ValidateLoginUseCase{
        return ValidateLoginUseCase()
    }
    @Provides
    fun provideValidatePasswordUseCase() : ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }
}