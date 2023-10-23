package com.example.moviecatalog.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.commons.components.Gender
import com.example.moviecatalog.commons.validation.usecases.ValidateDateUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateEmailUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateNameUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidateRepeatedPasswordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class RegistrationViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordsUseCase: ValidateRepeatedPasswordsUseCase

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope



    fun onNameChanged(newName: String) {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(

                )
            }
        }
    }

    fun onLoginChanged(newLogin: String) {
        scope.launch(Dispatchers.IO) {

            _uiState.update { currentState ->
                currentState.copy(
                )
            }
        }
    }

    fun onPasswordChanged(newPassword: String) {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                )
            }
        }
    }
    fun ondDateChanged(newDate: LocalDate) {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                )
            }
        }
    }

    private fun isError(
        login: String,
        password: String,
        isLoginCorrect: Boolean,
        isPasswordCorrect: Boolean
    ): Boolean {
        if (login.isBlank() || password.isBlank()) {
            return false
        }
        return !(isLoginCorrect && isPasswordCorrect)
    }

    private fun isButtonEnabled(login: String, password: String, isError: Boolean): Boolean {
        return login.isNotBlank() && password.isNotBlank() && !isError
    }
}


data class RegistrationUIState(
    val name: String = "",
    val isNameCorrect: Boolean = true,

    val gender: Gender = Gender.Male,

    val login: String = "",
    val isLoginCorrect: Boolean = true,

    val email: String = "",
    val isEmailCorrect: Boolean = true,

    val pickedDate: LocalDate = LocalDate.now(),
    val isDateCorrect: Boolean = true,

    val password: String = "",
    val isPasswordCorrect: Boolean = true,

    val repeatedPassword: String = "",
    val isPasswordsSame: Boolean = true,

    val isErrorFirstPage: Boolean = false,
    val isErrorSecondPage: Boolean = false,

    val isFirstButtonEnabled: Boolean = false,
    val isSecondButtonEnabled: Boolean = false
)