package com.example.moviecatalog.common.validation.domain.usecase

import java.time.LocalDate

class ValidateDateUseCase {
    fun execute(date: LocalDate?): Boolean{
        return date != LocalDate.now() && date != null
    }
}