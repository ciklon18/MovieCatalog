package com.example.moviecatalog.login.di

import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.validation.domain.usecase.LoginValidationUseCase
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
        loginValidationUseCase: LoginValidationUseCase,
        authRepository: AuthRepository,
        setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
    ): LoginScreenViewModel {
        return LoginScreenViewModel(
            setTokenToLocalStorageUseCase = setTokenToLocalStorageUseCase,
            loginValidationUseCase = loginValidationUseCase,
            authRepository = authRepository
        )
    }


}