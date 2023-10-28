package com.example.moviecatalog.common.validation.domain.usecase

import java.time.LocalDate
import javax.inject.Inject

class RegistrationValidationUseCase @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordsUseCase: ValidateRepeatedPasswordsUseCase
) {
    fun validateName(name : String) : Boolean {
        return validateNameUseCase.execute(name)
    }
    fun validateLogin(login : String) : Boolean {
        return validateLoginUseCase.execute(login)
    }
    fun validateEmail(email : String) : Boolean {
        return validateEmailUseCase.execute(email)
    }
    fun validateDate(date : LocalDate) : Boolean {
        return validateDateUseCase.execute(date)
    }
    fun validatePassword(password : String) : Boolean {
        return validatePasswordUseCase.execute(password)
    }
    fun validateRepeatedPassword(password : String, repeatedPassword: String) : Boolean {
        return validateRepeatedPasswordsUseCase.execute(password, repeatedPassword)
    }

}