package com.example.moviecatalog.login.di

import com.example.moviecatalog.common.auth.domain.usecase.LoginUserUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
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
        loginUserUseCase: LoginUserUseCase,
        setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase,
        getProfileUseCase: GetProfileUseCase,
        setProfileToLocalStorageUseCase: SetProfileToLocalStorageUseCase
    ): LoginScreenViewModel {
        return LoginScreenViewModel(
            setTokenToLocalStorageUseCase = setTokenToLocalStorageUseCase,
            loginValidationUseCase = loginValidationUseCase,
            loginUserUseCase = loginUserUseCase,
            getProfileUseCase = getProfileUseCase,
            setProfileToLocalStorageUseCase = setProfileToLocalStorageUseCase


        )
    }


}