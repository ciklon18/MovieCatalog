package com.example.moviecatalog.common.validation.domain.usecase


class ValidateNameUseCase {
    private val firstUpperLetterRegex = Regex("^[A-ZА-Я][a-zа-яё]+\\s*\$")
    fun execute(value: String): Boolean {
        return if (value.isBlank()) {
            false
        } else if (value.length < 2) {
            false
        } else if (value.any { it.isDigit() }) {
            false
        } else !(value.substring(1).any { it.isUpperCase() }) && value.matches(firstUpperLetterRegex)
    }
}