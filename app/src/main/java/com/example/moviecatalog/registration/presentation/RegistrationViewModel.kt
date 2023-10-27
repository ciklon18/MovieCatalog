package com.example.moviecatalog.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.auth.domain.model.UserRegisterModel
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.validation.domain.usecase.ValidateDateUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateEmailUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateLoginUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateNameUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidatePasswordUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateRepeatedPasswordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordsUseCase: ValidateRepeatedPasswordsUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    fun onNavigateUpPressed() {
        if (!_uiState.value.isFirstButtonPressed) {
            resetEnteredData()
        } else {
            scope.launch(Dispatchers.IO) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isFirstButtonPressed = false
                    )
                }
            }
        }
    }


    fun onFieldChanged(fieldType: FieldType, newValue: Any) {
        scope.launch(Dispatchers.IO) {
            when (fieldType) {
                FieldType.Name -> onNameChanged(newName = newValue as String)
                FieldType.Gender -> onGenderChanged(newGender = newValue as Gender)
                FieldType.Login -> onLoginChanged(newLogin = newValue as String)
                FieldType.Email -> onEmailChanged(newEmail = newValue as String)
                FieldType.BirthDate -> onBirthDateChanged(newBirthDate = newValue as LocalDate)
                FieldType.Password -> onPasswordChanged(newPassword = newValue as String)
                FieldType.RepeatedPassword -> onRepeatedPasswordChanged(
                    password = _uiState.value.password, newRepeatedPassword = newValue as String
                )
            }
            updateErrorAndButtonStateForField(fieldType)

        }
    }


    private fun onNameChanged(newName: String) {
        scope.launch(Dispatchers.IO) {
            val isNameCorrect = validateNameUseCase.execute(newName)
            _uiState.update { currentState ->
                currentState.copy(
                    name = newName, isNameCorrect = isNameCorrect
                )
            }
        }
    }

    private fun onGenderChanged(newGender: Gender) {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    gender = newGender
                )
            }
        }
    }

    private fun onLoginChanged(newLogin: String) {
        scope.launch(Dispatchers.IO) {
            val isLoginCorrect = validateLoginUseCase.execute(newLogin)
            _uiState.update { currentState ->
                currentState.copy(
                    login = newLogin, isLoginCorrect = isLoginCorrect
                )
            }
        }
    }

    private fun onEmailChanged(newEmail: String) {
        scope.launch(Dispatchers.IO) {
            val isEmailCorrect = validateEmailUseCase.execute(newEmail)
            _uiState.update { currentState ->
                currentState.copy(
                    email = newEmail, isEmailCorrect = isEmailCorrect
                )
            }
        }
    }


    private fun onBirthDateChanged(newBirthDate: LocalDate) {
        scope.launch(Dispatchers.IO) {
            val isBirthDateCorrect = validateDateUseCase.execute(newBirthDate)
            _uiState.update { currentState ->
                currentState.copy(
                    birthDate = newBirthDate, isBirthDateCorrect = isBirthDateCorrect
                )
            }
        }
    }

    private fun onPasswordChanged(newPassword: String) {
        scope.launch(Dispatchers.IO) {
            val isPasswordCorrect = validatePasswordUseCase.execute(newPassword)
            _uiState.update { currentState ->
                currentState.copy(
                    password = newPassword, isPasswordCorrect = isPasswordCorrect
                )
            }
        }
    }

    private fun onRepeatedPasswordChanged(password: String, newRepeatedPassword: String) {
        scope.launch(Dispatchers.IO) {
            val isPasswordsSame =
                validateRepeatedPasswordsUseCase.execute(password, newRepeatedPassword)
            _uiState.update { currentState ->
                currentState.copy(
                    repeatedPassword = newRepeatedPassword, isPasswordsSame = isPasswordsSame
                )
            }
        }
    }

    private fun updateErrorAndButtonStateForField(fieldType: FieldType) {
        scope.launch(Dispatchers.IO) {
            if (fieldType != FieldType.Gender) {
                if (fieldType == FieldType.Password || fieldType == FieldType.RepeatedPassword) {
                    _uiState.update { currentState ->
                        val isSecondPageDataValid = isSecondPageDataValid(currentState)
                        currentState.copy(
                            isErrorSecondPage = !isSecondPageDataValid,
                            isSecondButtonEnabled = isButtonEnabledForSecondPage(
                                currentState,
                                isSecondPageDataValid
                            )
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        val isFirstPageDataValid = isFirstPageDataValid(currentState)
                        currentState.copy(
                            isErrorFirstPage = !isFirstPageDataValid,
                            isFirstButtonEnabled = isButtonEnabledForFirstPage(
                                currentState,
                                isFirstPageDataValid
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onFirstButtonPressed() {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    isFirstButtonPressed = true
                )
            }
        }
    }

    fun onSecondButtonPressed(navController: NavHostController) {
        scope.launch(Dispatchers.IO) {
            try {
                val user = UserRegisterModel(
                    name = _uiState.value.name,
                    password = _uiState.value.password,
                    userName = _uiState.value.login,
                    email = _uiState.value.email,
                    birthDate = _uiState.value.birthDate.toString(),
                    gender = if (_uiState.value.gender == Gender.Male) 0 else 1
                )
                val response = authRepository.register(user)

                withContext(Dispatchers.Main) {
                    navController.navigate(Routes.SelectAuthScreen.name)
                }

                // обработать response
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isErrorSecondPage = true, isSecondButtonEnabled = false
                    )
                }
            }
        }
    }

    private fun isButtonEnabledForFirstPage(
        currentState: RegistrationUIState,
        isDataValid: Boolean
    ): Boolean {
        return currentState.name.isNotBlank()
                && currentState.login.isNotBlank()
                && currentState.email.isNotBlank()
                && currentState.birthDate != LocalDate.now()
                && currentState.birthDate != null
                && isDataValid
    }

    private fun isFirstPageDataValid(currentState: RegistrationUIState): Boolean {
        if (currentState.name.isBlank() && currentState.login.isBlank()
            && currentState.email.isBlank() && currentState.birthDate == null
        ) {
            return true
        }
        return currentState.isNameCorrect
                && currentState.isEmailCorrect
                && currentState.isBirthDateCorrect
                && currentState.isLoginCorrect
    }

    private fun isButtonEnabledForSecondPage(
        currentState: RegistrationUIState,
        isDataValid: Boolean
    ): Boolean {
        return currentState.password.isNotBlank()
                && currentState.repeatedPassword.isNotBlank()
                && isDataValid
    }

    private fun isSecondPageDataValid(currentState: RegistrationUIState): Boolean {
        if (currentState.password.isBlank() && currentState.repeatedPassword.isBlank()) {
            return true
        }
        return currentState.isPasswordCorrect && currentState.isPasswordsSame
    }

    private fun resetEnteredData() {
        _uiState.value = RegistrationUIState()
    }
}


data class RegistrationUIState(
    val name: String = "", val isNameCorrect: Boolean = true,

    val gender: Gender = Gender.Male,

    val login: String = "", val isLoginCorrect: Boolean = true,

    val email: String = "", val isEmailCorrect: Boolean = true,

    val birthDate: LocalDate? = null, val isBirthDateCorrect: Boolean = true,

    val password: String = "", val isPasswordCorrect: Boolean = true,

    val repeatedPassword: String = "", val isPasswordsSame: Boolean = true,

    val isErrorFirstPage: Boolean = false, val isErrorSecondPage: Boolean = false,

    val isFirstButtonEnabled: Boolean = false, val isSecondButtonEnabled: Boolean = false,

    val isFirstButtonPressed: Boolean = false
) {

}