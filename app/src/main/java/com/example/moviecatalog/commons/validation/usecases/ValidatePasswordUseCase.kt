package com.example.moviecatalog.commons.validation.usecases



class ValidatePasswordUseCase {
    fun execute(value: String): Boolean {
        return value.isNotBlank()
    }
}