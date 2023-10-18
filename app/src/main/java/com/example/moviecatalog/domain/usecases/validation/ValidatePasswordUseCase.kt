package com.example.moviecatalog.domain.usecases.validation

import com.example.moviecatalog.domain.models.ValidationResult


class ValidatePasswordUseCase {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(isWrong = true)
        }
        return ValidationResult(isWrong = false)
    }
}