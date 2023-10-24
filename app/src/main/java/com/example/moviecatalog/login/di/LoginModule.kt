package com.example.moviecatalog.login.di

import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import com.example.moviecatalog.login.presentation.LoginScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    fun provideLoginScreenViewModel(
        validateLoginUseCase: ValidateLoginUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase
    ): LoginScreenViewModel {
        return LoginScreenViewModel(validateLoginUseCase, validatePasswordUseCase)
    }
}