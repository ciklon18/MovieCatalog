package com.example.moviecatalog.common.validation.di

import com.example.moviecatalog.common.validation.domain.usecase.ValidateDateUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateEmailUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateLoginUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateNameUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidatePasswordUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateRepeatedPasswordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ValidationModule {
    @Provides
    fun provideValidateDateUseCase() : ValidateDateUseCase {
        return ValidateDateUseCase()
    }
    @Provides
    fun provideValidateEmailUseCase() : ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    fun provideValidateLoginUseCase() : ValidateLoginUseCase {
        return ValidateLoginUseCase()
    }
    @Provides
    fun provideValidateNameUseCase() : ValidateNameUseCase {
        return ValidateNameUseCase()
    }
    @Provides
    fun provideValidatePasswordUseCase() : ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideValidateRepeatedPasswordsUseCase() : ValidateRepeatedPasswordsUseCase {
        return ValidateRepeatedPasswordsUseCase()
    }

}