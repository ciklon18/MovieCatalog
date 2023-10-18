package com.example.moviecatalog.domain.usecases.validation


import com.example.moviecatalog.domain.models.ValidationResult


class ValidateLoginUseCase{
    fun execute(login: String): ValidationResult {
        if (login.isBlank()){
            return ValidationResult(isWrong = true)
        }
        return ValidationResult(isWrong = false)
    }
}