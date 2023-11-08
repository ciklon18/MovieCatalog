package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import com.example.moviecatalog.common.validation.domain.util.ErrorMessageType

class ValidateRepeatedPasswordsUseCase {
    fun execute(password: String, repeatedPassword: String): ValidationResult{
        return if (repeatedPassword.isBlank()){
            ValidationResult(true, ErrorMessageType.BLANK_REPEATED_PASSWORD)
        } else if (password != repeatedPassword){
            ValidationResult(true, ErrorMessageType.NOT_MATCHED_PASSWORDS)
        } else {
            ValidationResult(false)
        }
    }
}