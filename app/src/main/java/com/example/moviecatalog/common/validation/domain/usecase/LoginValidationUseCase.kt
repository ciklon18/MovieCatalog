package com.example.moviecatalog.common.validation.domain.usecase

import javax.inject.Inject

class LoginValidationUseCase @Inject constructor(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {
    fun validateLogin(login: String) : Boolean{
        return validateLoginUseCase.execute(login)
    }

    fun validatePassword(password: String) : Boolean {
        return validatePasswordUseCase.execute(password)
    }

}