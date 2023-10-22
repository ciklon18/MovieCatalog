package com.example.moviecatalog.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.commons.validation.usecases.ValidateLoginUseCase
import com.example.moviecatalog.commons.validation.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    fun onLoginChanged(newLogin: String) {
        scope.launch(Dispatchers.IO) {
            val isLoginCorrect = validateLoginUseCase.execute(newLogin)
            val isPasswordCorrect = validatePasswordUseCase.execute(_uiState.value.password)
            val isError = isError(
                newLogin,
                _uiState.value.password,
                isLoginCorrect,
                isPasswordCorrect
            )
            _uiState.update { currentState ->
                currentState.copy(
                    login = newLogin,
                    isLoginCorrect = isLoginCorrect,
                    isError = isError,
                    isButtonEnabled = isButtonEnabled(newLogin, currentState.password, isError)
                )
            }
        }
    }
    fun setFalse(){
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    isError = true,
                    isButtonEnabled = false
               )
            }
        }
    }

    fun onPasswordChanged(newPassword: String) {
        scope.launch(Dispatchers.IO) {
            val isLoginCorrect = validatePasswordUseCase.execute(_uiState.value.login)
            val isPasswordCorrect = validatePasswordUseCase.execute(newPassword)
            val isError = isError(
                _uiState.value.login,
                newPassword,
                isLoginCorrect,
                isPasswordCorrect
            )
            _uiState.update { currentState ->
                currentState.copy(
                    password = newPassword,
                    isPasswordCorrect = isPasswordCorrect,
                    isError = isError,
                    isButtonEnabled = isButtonEnabled(currentState.login, newPassword, isError)
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


data class LoginUIState(
    val login: String = "",
    val isLoginCorrect: Boolean = true,
    val password: String = "",
    val isPasswordCorrect: Boolean = true,
    val isError: Boolean = false,
    val isButtonEnabled: Boolean = false
)