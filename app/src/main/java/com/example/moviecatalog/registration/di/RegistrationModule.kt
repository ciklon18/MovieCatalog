package com.example.moviecatalog.registration.di

import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateDateUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateEmailUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateLoginUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateNameUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidatePasswordUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateRepeatedPasswordsUseCase
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
        validateNameUseCase: ValidateNameUseCase,
        validateLoginUseCase: ValidateLoginUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        validateDateUseCase: ValidateDateUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase,
        validateRepeatedPasswordsUseCase: ValidateRepeatedPasswordsUseCase,
        authRepository: AuthRepository,
        setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
    ): RegistrationViewModel {
        return RegistrationViewModel(
            validateNameUseCase = validateNameUseCase,
            validateLoginUseCase = validateLoginUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validateDateUseCase = validateDateUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            validateRepeatedPasswordsUseCase = validateRepeatedPasswordsUseCase,
            authRepository = authRepository,
            setTokenToLocalStorageUseCase = setTokenToLocalStorageUseCase
        )
    }
}