package com.example.moviecatalog.common.validation.domain.usecase

class ValidateRepeatedPasswordsUseCase {
    fun execute(password: String, repeatedPassword: String): Boolean{
        return repeatedPassword.isNotBlank() && password == repeatedPassword
    }
}