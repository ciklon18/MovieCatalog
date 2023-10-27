package com.example.moviecatalog.login.di

import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.validation.domain.usecase.ValidateLoginUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidatePasswordUseCase
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
        validatePasswordUseCase: ValidatePasswordUseCase,
        authRepository: AuthRepository,
    ): LoginScreenViewModel {
        return LoginScreenViewModel(
            validateLoginUseCase = validateLoginUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            authRepository = authRepository
        )
    }


}