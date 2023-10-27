package com.example.moviecatalog.common.validation.domain.usecase



class ValidatePasswordUseCase {
    fun execute(value: String): Boolean {
        return value.isNotBlank()
    }
}