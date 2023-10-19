package com.example.moviecatalog.commons.validation.usecases


class ValidateLoginUseCase{
    fun execute(value: String): Boolean {
        return value.isNotBlank()
    }
}