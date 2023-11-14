package com.example.moviecatalog.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.auth.data.mapper.toUserLoginModel
import com.example.moviecatalog.common.auth.domain.usecase.LoginUserUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.validation.domain.usecase.LoginValidationUseCase
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
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginValidationUseCase: LoginValidationUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val setProfileToLocalStorageUseCase: SetProfileToLocalStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope


    @OptIn(FlowPreview::class)
    fun onFieldChanged(fieldType: FieldType, newValue: Any) {
        viewModelScope.launch {
            val valueFlow = flowOf(newValue as String)
                .debounce(300)
                .distinctUntilChanged()
            when (fieldType) {
                FieldType.Login -> {
                    valueFlow.collect { newLogin ->
                        onLoginChanged(newLogin)
                        updateErrorAndButtonStateForField()
                    }
                }

                FieldType.Password -> {
                    valueFlow.collect { newPassword ->
                        onPasswordChanged(newPassword)
                        updateErrorAndButtonStateForField()
                    }
                }

                else -> {}
            }
        }


    }

    private fun updateErrorAndButtonStateForField() {

        _uiState.update { currentState ->
            val isDataValid = loginValidationUseCase.isDataValid(
                currentState.login, currentState.password,
                currentState.loginErrorMessage, currentState.passwordErrorMessage
            )
            currentState.copy(
                isError = !isDataValid,
                isButtonEnabled = loginValidationUseCase.isButtonEnabled(
                    currentState.login,
                    currentState.password,
                    isDataValid
                )
            )
        }
    }

    private fun onLoginChanged(newLogin: String) {
        val loginValidationResult = loginValidationUseCase.validateLogin(newLogin)

        _uiState.update { currentState ->
            currentState.copy(
                login = newLogin,
                loginErrorMessage = loginValidationResult.errorMessage,
            )
        }
    }


    private fun onPasswordChanged(newPassword: String) {
        val passwordValidationResult = loginValidationUseCase.validatePassword(newPassword)
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                passwordErrorMessage = passwordValidationResult.errorMessage,
            )
        }
    }

    fun onButtonPressed() {
        scope.launch(Dispatchers.Default) {
            try {
                val user = _uiState.value.toUserLoginModel()
                val response = loginUserUseCase.execute(user)
                val token = response.getOrNull()?.token
                token?.let { setTokenToLocalStorageUseCase.execute(it) }
                if (token != null) {
                    val result = getProfileUseCase.execute(token)
                    val profile = result.getOrNull()
                    if (result.isSuccess && profile != null) {
                        setProfileToLocalStorageUseCase.execute(profile)
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            isButtonPressed = true
                        )
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
                    isError = true, isButtonEnabled = false
                )
            }
        }

    }
}


data class LoginUIState(
    val login: String = "",
    val loginErrorMessage: Int? = null,
    val password: String = "",
    val passwordErrorMessage: Int? = null,
    val isError: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isButtonPressed: Boolean = false
)