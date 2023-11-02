package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import com.example.moviecatalog.common.validation.domain.util.ErrorMessageType


class ValidateNameUseCase {
    fun execute(value: String): ValidationResult {
        return if (value.length < 2){
            ValidationResult(true, ErrorMessageType.SHORT_NAME)
        } else if (!value.all { it.isLetter() }){
            ValidationResult(true, ErrorMessageType.INCORRECT_NAME)
        } else {
            ValidationResult(false)
        }
    }
}