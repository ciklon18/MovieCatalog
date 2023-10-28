package com.example.moviecatalog.common.validation.domain.usecase


class ValidateNameUseCase {
    private val firstUpperLetterRegex = Regex("^[A-ZА-Я][a-zа-яё]+\\s*\$")
    fun execute(value: String): Boolean {
        return value.isNotBlank()
                && !(value.any { it.isDigit() })
                && value.matches(firstUpperLetterRegex)
                && value.length > 1
    }
}