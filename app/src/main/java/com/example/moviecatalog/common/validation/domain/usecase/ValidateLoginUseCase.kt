package com.example.moviecatalog.common.validation.domain.usecase


class ValidateLoginUseCase {
    fun execute(value: String): Boolean {
        return value.length > 3
                && value.any { it.isLetter() }
                && (value.all { it.isLetterOrDigit() } || value.any { it.isDigit() })
    }
}
