package com.example.moviecatalog.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.auth.data.mapper.toUserRegisterModel
import com.example.moviecatalog.common.auth.domain.usecase.RegisterUserUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.validation.domain.usecase.RegistrationValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase,
    private val registrationValidationUseCase: RegistrationValidationUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val setProfileToLocalStorageUseCase: SetProfileToLocalStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState: StateFlow<RegistrationUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope


    fun onNavigateUpPressed() {
        if (!_uiState.value.isFirstButtonPressed) {
            resetEnteredData()
        } else {
            scope.launch(Dispatchers.Default) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isFirstButtonPressed = false
                    )
                }
            }
        }
    }


    @OptIn(FlowPreview::class)
    fun onFieldChanged(fieldType: FieldType, newValue: Any) {
        val valueFlow = flowOf(newValue)
            .debounce(500)
            .distinctUntilChanged()
        scope.launch {
            when (fieldType) {

                FieldType.Name -> valueFlow.collect { newName ->
                    onNameChanged(newName as String)
                }

                FieldType.Gender -> valueFlow.collect { newGender ->
                    onGenderChanged(newGender as Gender)
                }

                FieldType.Login -> valueFlow.collect { newLogin ->
                    onLoginChanged(newLogin as String)
                }

                FieldType.Email -> valueFlow.collect { newEmail ->
                    onEmailChanged(newEmail as String)
                }

                FieldType.BirthDate -> valueFlow.collect { newBirthDate ->
                    onBirthDateChanged(newBirthDate as LocalDate)
                }

                FieldType.Password -> valueFlow.collect { newPassword ->
                    onPasswordChanged(newPassword as String)
                }

                FieldType.RepeatedPassword -> valueFlow.collect { newRepeatedPassword ->
                    onRepeatedPasswordChanged(newRepeatedPassword as String)
                }

                else -> {}
            }
            updateErrorAndButtonStateForField(fieldType)
        }
    }


    private fun onNameChanged(newName: String) {
        val nameValidationResult = registrationValidationUseCase.validateName(newName)
        _uiState.update { currentState ->
            currentState.copy(
                name = newName, nameErrorMessage = nameValidationResult.errorMessage
            )
        }
    }

    private fun onGenderChanged(newGender: Gender) {
        _uiState.update { currentState ->
            currentState.copy(
                gender = newGender
            )
        }
    }

    private fun onLoginChanged(newLogin: String) {
        val loginValidationResult = registrationValidationUseCase.validateLogin(newLogin)
        _uiState.update { currentState ->
            currentState.copy(
                login = newLogin, loginErrorMessage = loginValidationResult.errorMessage
            )
        }
    }

    private fun onEmailChanged(newEmail: String) {
        val emailValidationResult = registrationValidationUseCase.validateEmail(newEmail)
        _uiState.update { currentState ->
            currentState.copy(
                email = newEmail, emailErrorMessage = emailValidationResult.errorMessage
            )
        }
    }


    private fun onBirthDateChanged(newBirthDate: LocalDate) {
        val birthDateValidationResult = registrationValidationUseCase.validateDate(newBirthDate)
        _uiState.update { currentState ->
            currentState.copy(
                birthDate = newBirthDate,
                birthDateErrorMessage = birthDateValidationResult.errorMessage
            )
        }
    }

    private fun onPasswordChanged(newPassword: String) {
        val passwordValidationResult =
            registrationValidationUseCase.validatePassword(newPassword)
        val repeatedPasswordValidationResult =
            registrationValidationUseCase.validateRepeatedPassword(
                newPassword, _uiState.value.repeatedPassword
            )
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                passwordErrorMessage = passwordValidationResult.errorMessage,
                repeatedPasswordErrorMessage = repeatedPasswordValidationResult.errorMessage,
                isRegisterError = false
            )
        }
    }

    private fun onRepeatedPasswordChanged(newRepeatedPassword: String) {
        val passwordValidationResult =
            registrationValidationUseCase.validatePassword(_uiState.value.password)
        val repeatedPasswordValidationResult =
            registrationValidationUseCase.validateRepeatedPassword(
                _uiState.value.password, newRepeatedPassword
            )
        _uiState.update { currentState ->
            currentState.copy(
                repeatedPassword = newRepeatedPassword,
                passwordErrorMessage = passwordValidationResult.errorMessage,
                repeatedPasswordErrorMessage = repeatedPasswordValidationResult.errorMessage,
                isRegisterError = false
            )
        }
    }

    private fun updateErrorAndButtonStateForField(fieldType: FieldType) {
        if (fieldType == FieldType.Gender) {
            return
        }
        if (fieldType == FieldType.Password || fieldType == FieldType.RepeatedPassword) {
            _uiState.update { currentState ->
                val isSecondPageValid = registrationValidationUseCase.validateSecondPage(
                    password = currentState.password,
                    repeatedPassword = currentState.repeatedPassword,
                    passwordErrorMessage = currentState.passwordErrorMessage,
                    repeatedPasswordErrorMessage = currentState.repeatedPasswordErrorMessage
                )
                currentState.copy(
                    passwordErrorMessage = if (isSecondPageValid) null else currentState.passwordErrorMessage,
                    repeatedPasswordErrorMessage = if (isSecondPageValid) null else currentState.repeatedPasswordErrorMessage,
                    isSecondButtonEnabled = registrationValidationUseCase.isButtonEnabledForSecondPage(
                        password = currentState.password,
                        repeatedPassword = currentState.repeatedPassword,
                        isPageValid = isSecondPageValid
                    )
                )
            }
        } else {
            _uiState.update { currentState ->
                val isFirstPageValid = registrationValidationUseCase.validateFirstPage(
                    name = currentState.name,
                    login = currentState.login,
                    email = currentState.email,
                    birthDate = currentState.birthDate,
                    nameErrorMessage = currentState.nameErrorMessage,
                    loginErrorMessage = currentState.loginErrorMessage,
                    emailErrorMessage = currentState.emailErrorMessage,
                    birthDateErrorMessage = currentState.birthDateErrorMessage
                )
                currentState.copy(
                    isFirstButtonEnabled = registrationValidationUseCase.isButtonEnabledForFirstPage(
                        name = currentState.name,
                        login = currentState.login,
                        email = currentState.email,
                        birthDate = currentState.birthDate,
                        isPageValid = isFirstPageValid
                    )
                )
            }
        }
    }

    fun onFirstButtonPressed() {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(
                    isFirstButtonPressed = true
                )
            }
        }
    }

    fun onSecondButtonPressed() {
        scope.launch(Dispatchers.Default) {
            try {
                val user = _uiState.value.toUserRegisterModel()
                val response = registerUserUseCase.execute(user)
                val token = response.getOrNull()?.token ?: ""
                if (token.isNotBlank()) {
                    token.let { setTokenToLocalStorageUseCase.execute(it) }
                    getProfileUseCase.execute(token).getOrNull()?.let { profile ->
                        setProfileToLocalStorageUseCase.execute(profile)
                        _uiState.update { currentState ->
                            currentState.copy(
                                isSecondButtonPressed = true
                            )
                        }
                    }
                } else {
                    handleException()
                }

            } catch (e: Exception) {
                handleException()
            }
        }
    }


    private fun handleException() {
        scope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(
                    isRegisterError = true, isSecondButtonEnabled = false
                )
            }
        }
    }


    private fun resetEnteredData() {
        _uiState.value = RegistrationUIState()
    }
}


data class RegistrationUIState(
    val name: String = "", val nameErrorMessage: Int? = null,

    val gender: Gender = Gender.Male,

    val login: String = "", val loginErrorMessage: Int? = null,

    val email: String = "", val emailErrorMessage: Int? = null,

    val birthDate: LocalDate? = null, val birthDateErrorMessage: Int? = null,

    val password: String = "", val passwordErrorMessage: Int? = null,

    val repeatedPassword: String = "", val repeatedPasswordErrorMessage: Int? = null,

    val isRegisterError: Boolean = false,

    val isFirstButtonEnabled: Boolean = false, val isSecondButtonEnabled: Boolean = false,

    val isFirstButtonPressed: Boolean = false, val isSecondButtonPressed: Boolean = false
)