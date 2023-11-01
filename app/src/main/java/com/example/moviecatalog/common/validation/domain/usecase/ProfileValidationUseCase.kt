package com.example.moviecatalog.common.validation.domain.usecase

import java.time.LocalDate
import javax.inject.Inject


class ProfileValidationUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateLinkUseCase: ValidateLinkUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase
) {
    fun validateEmail(email: String) : Boolean{
        return validateEmailUseCase.execute(email)
    }

    fun validateLink(link: String) : Boolean {
        return validateLinkUseCase.execute(link)
    }

    fun validateName(name: String) : Boolean {
        return validateNameUseCase.execute(name)
    }

    fun validateDate(date: LocalDate) : Boolean {
        return validateDateUseCase.execute(date)
    }
}