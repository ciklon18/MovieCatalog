package com.example.moviecatalog.registration.di

import com.example.moviecatalog.common.auth.domain.usecase.RegisterUserUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.validation.domain.usecase.RegistrationValidationUseCase
import com.example.moviecatalog.registration.presentation.RegistrationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RegistrationModule {
    @Provides
    fun provideRegistrationViewModel(
        registrationValidationUseCase: RegistrationValidationUseCase,
        registerUserUseCase: RegisterUserUseCase,
        setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase,
        setProfileToLocalStorageUseCase: SetProfileToLocalStorageUseCase
    ): RegistrationViewModel {
        return RegistrationViewModel(
            setTokenToLocalStorageUseCase = setTokenToLocalStorageUseCase,
            registrationValidationUseCase = registrationValidationUseCase,
            registerUserUseCase = registerUserUseCase,
            setProfileToLocalStorageUseCase = setProfileToLocalStorageUseCase
        )
    }
}