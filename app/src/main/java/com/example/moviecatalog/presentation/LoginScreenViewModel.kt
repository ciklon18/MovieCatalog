package com.example.moviecatalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.usecases.validation.ValidateLoginUseCase
import com.example.moviecatalog.domain.usecases.validation.ValidatePasswordUseCase
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


    fun onLoginChanged(newLogin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isLoginBlank = newLogin.isBlank()
            val isLoginWrong = !isLoginBlank && validateLoginUseCase.execute(newLogin).isWrong

            _uiState.update { currentState ->
                currentState.copy(
                    login = newLogin,
                    isLoginWrong = isLoginWrong,
                    isButtonEnabled = currentState.password.isNotBlank() && !isLoginBlank
                )
            }
        }
    }


    fun onPasswordChanged(newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isPasswordBlank = newPassword.isBlank()
            val isPasswordWrong = !isPasswordBlank && validatePasswordUseCase.execute(newPassword).isWrong

            _uiState.update { currentState ->
                currentState.copy(
                    password = newPassword,
                    isPasswordWrong = isPasswordWrong,
                    isButtonEnabled = currentState.login.isNotBlank() && !isPasswordBlank
                )
            }
        }
    }

}


data class LoginUIState(
    val login: String = "",
    val isLoginWrong: Boolean = false,
    val password: String = "",
    val isPasswordWrong: Boolean = false,
    val isButtonEnabled: Boolean = false
)