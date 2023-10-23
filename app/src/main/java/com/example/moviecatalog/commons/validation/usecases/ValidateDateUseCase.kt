package com.example.moviecatalog.commons.validation.usecases

class ValidateDateUseCase {
    fun execute(date: String): Boolean{
        return date.isNotBlank()
    }
}