package com.example.moviecatalog.registration.di

import com.example.moviecatalog.commons.validation.usecases.ValidateDateUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateEmailUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateNameUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateRepeatedPasswordsUseCase
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
        validateRepeatedPasswordsUseCase: ValidateRepeatedPasswordsUseCase
    ): RegistrationViewModel {
        return RegistrationViewModel(
            validateNameUseCase = validateNameUseCase,
            validateLoginUseCase = validateLoginUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validateDateUseCase = validateDateUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            validateRepeatedPasswordsUseCase = validateRepeatedPasswordsUseCase
        )
    }
}