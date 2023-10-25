package com.example.moviecatalog.commons.validation.di

import com.example.moviecatalog.commons.validation.usecases.ValidateDateUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateEmailUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateNameUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateRepeatedPasswordsUseCase
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
    fun provideValidateEmailUseCase() : ValidateEmailUseCase{
        return ValidateEmailUseCase()
    }

    @Provides
    fun provideValidateLoginUseCase() : ValidateLoginUseCase{
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