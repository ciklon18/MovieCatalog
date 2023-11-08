package com.example.moviecatalog.common.validation.domain.usecase

import com.example.moviecatalog.common.validation.domain.model.ValidationResult
import java.time.LocalDate
import javax.inject.Inject


class ProfileValidationUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateLinkUseCase: ValidateLinkUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase
) {
    fun validateEmail(email: String): ValidationResult {
        return validateEmailUseCase.execute(email)
    }

    fun validateLink(link: String): ValidationResult {
        return validateLinkUseCase.execute(link)
    }

    fun validateName(name: String): ValidationResult {
        return validateNameUseCase.execute(name)
    }

    fun validateDate(date: LocalDate): ValidationResult {
        return validateDateUseCase.execute(date)
    }

    fun isDataValid(
        emailErrorMessage: Int?,
        linkErrorMessage: Int?,
        nameErrorMessage: Int?,
        birthDateErrorMessage: Int?
    ): Boolean {
        return emailErrorMessage == null
                && linkErrorMessage == null
                && nameErrorMessage == null
                && birthDateErrorMessage == null
    }
}