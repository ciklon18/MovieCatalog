package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import com.example.moviecatalog.common.validation.domain.util.ErrorMessageType

class ValidatePasswordUseCase {
    fun execute(value: String): ValidationResult {
        return if (value.isBlank()){
            ValidationResult(true, ErrorMessageType.BLANK_PASSWORD)
        } else {
            ValidationResult(false)
        }
    }
}