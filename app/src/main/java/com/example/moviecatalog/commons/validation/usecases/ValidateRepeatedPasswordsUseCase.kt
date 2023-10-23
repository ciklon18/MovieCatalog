package com.example.moviecatalog.commons.validation.usecases

class ValidateRepeatedPasswordsUseCase {
    fun execute(password: String, repeatedPassword: String): Boolean{
        return repeatedPassword.isNotBlank() && password == repeatedPassword
    }
}