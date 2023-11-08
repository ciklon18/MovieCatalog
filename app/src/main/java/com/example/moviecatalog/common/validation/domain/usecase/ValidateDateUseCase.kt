package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import com.example.moviecatalog.common.validation.domain.util.ErrorMessageType
import java.time.LocalDate

class ValidateDateUseCase {
    fun execute(date: LocalDate?): ValidationResult {
        return if (date == null) {
            ValidationResult(true, ErrorMessageType.BLANK_DATE)
        } else if (date.isBefore(LocalDate.of(1900, 1, 1))) {
            ValidationResult(true, ErrorMessageType.INCORRECT_DATE)
        } else {
            ValidationResult(false)
        }
    }
}