package com.example.moviecatalog.commons.validation.usecases

import java.time.LocalDate

class ValidateDateUseCase {
    fun execute(date: LocalDate?): Boolean{
        return date != LocalDate.now() && date != null
    }
}