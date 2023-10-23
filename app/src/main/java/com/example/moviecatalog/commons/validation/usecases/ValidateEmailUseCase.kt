package com.example.moviecatalog.commons.validation.usecases

class ValidateEmailUseCase {
    private val emailRegex = Regex("^[\\dA-Za-z\\-_.]+@[\\dA-Za-z\\-]+\\.[\\dA-Za-z]+\$")
    fun execute(value: String): Boolean {
        return value.isNotBlank() && value.matches(emailRegex)
    }
}