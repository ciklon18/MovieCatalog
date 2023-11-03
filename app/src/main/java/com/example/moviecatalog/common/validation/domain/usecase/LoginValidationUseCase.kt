package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import javax.inject.Inject

class LoginValidationUseCase @Inject constructor(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {
    fun validateLogin(login: String) : ValidationResult{
        return validateLoginUseCase.execute(login)
    }

    fun validatePassword(password: String) : ValidationResult {
        return validatePasswordUseCase.execute(password)
    }

    fun isDataValid(login: String, password: String, loginErrorMessage: Int?, passwordErrorMessage: Int?): Boolean {
        if (login.isBlank() && password.isBlank()) {
            return true
        }
        return loginErrorMessage == null && passwordErrorMessage == null
    }
    fun isButtonEnabled(login: String, password: String, isDataValid: Boolean): Boolean {
        return login.isNotBlank() && password.isNotBlank() && isDataValid
    }

}