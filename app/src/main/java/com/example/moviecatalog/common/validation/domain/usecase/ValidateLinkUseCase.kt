package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import com.example.moviecatalog.common.validation.domain.util.ErrorMessageType

class ValidateLinkUseCase {
    private val linkRegex =
        Regex("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\$")
    fun execute(link: String): ValidationResult {
        return if (link.isBlank()){
            ValidationResult(false)
        } else if (!link.matches(linkRegex)){
            ValidationResult(true, ErrorMessageType.INCORRECT_LINK)
        } else {
            ValidationResult(false)
        }
    }
}