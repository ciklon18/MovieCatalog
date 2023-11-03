package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import com.example.moviecatalog.common.validation.domain.util.ErrorMessageType

class ValidateLoginUseCase {
    fun execute(value: String): ValidationResult {
        val isLengthValid = value.length > 3

        val isAlphanumeric = value.all { it.isLetterOrDigit() }
        return if (!isLengthValid) {
            ValidationResult(true, ErrorMessageType.SHORT_LOGIN)
        } else if (!(isAlphanumeric)) {
            ValidationResult(true, ErrorMessageType.INCORRECT_LOGIN)
        } else {
            ValidationResult(false)
        }
    }
}
