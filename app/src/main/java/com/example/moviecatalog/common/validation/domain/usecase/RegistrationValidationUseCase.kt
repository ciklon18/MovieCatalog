package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import java.time.LocalDate
import javax.inject.Inject

class RegistrationValidationUseCase @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordsUseCase: ValidateRepeatedPasswordsUseCase
) {
    fun validateName(name: String): ValidationResult {
        return validateNameUseCase.execute(name)
    }

    fun validateLogin(login: String): ValidationResult {
        return validateLoginUseCase.execute(login)
    }

    fun validateEmail(email: String): ValidationResult {
        return validateEmailUseCase.execute(email)
    }

    fun validateDate(date: LocalDate): ValidationResult {
        return validateDateUseCase.execute(date)
    }

    fun validatePassword(password: String): ValidationResult {
        return validatePasswordUseCase.execute(password)
    }

    fun validateRepeatedPassword(password: String, repeatedPassword: String): ValidationResult {
        return validateRepeatedPasswordsUseCase.execute(password, repeatedPassword)
    }


    fun validateFirstPage(
        name: String,
        login: String,
        email: String,
        birthDate: LocalDate?,
        nameErrorMessage: Int?,
        loginErrorMessage: Int?,
        emailErrorMessage: Int?,
        birthDateErrorMessage: Int?
    ): Boolean {
        if (name.isBlank() && login.isBlank()
            && email.isBlank() && birthDate == null
        ) {
            return true
        }
        return nameErrorMessage == null
                && loginErrorMessage == null
                && emailErrorMessage == null
                && birthDateErrorMessage == null
    }

    fun validateSecondPage(
        password: String,
        repeatedPassword: String,
        passwordErrorMessage: Int?,
        repeatedPasswordErrorMessage: Int?
    ): Boolean {
        if (password.isBlank() && repeatedPassword.isBlank()) {
            return true
        }
        return passwordErrorMessage == null && repeatedPasswordErrorMessage == null
    }

    fun isButtonEnabledForFirstPage(
        name: String,
        login: String,
        email: String,
        birthDate: LocalDate?,
        isPageValid: Boolean
    ): Boolean {
        return name.isNotBlank()
                && login.isNotBlank()
                && email.isNotBlank()
                && birthDate != null
                && isPageValid
    }

    fun isButtonEnabledForSecondPage(
        password: String,
        repeatedPassword: String,
        isPageValid: Boolean
    ): Boolean {
        return password.isNotBlank()
                && repeatedPassword.isNotBlank()
                && isPageValid
    }


}