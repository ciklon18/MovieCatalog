package com.example.moviecatalog.registration.di

import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
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
        authRepository: AuthRepository
    ): RegistrationViewModel {
        return RegistrationViewModel(
            registrationValidationUseCase = registrationValidationUseCase,
            authRepository = authRepository
        )
    }
}