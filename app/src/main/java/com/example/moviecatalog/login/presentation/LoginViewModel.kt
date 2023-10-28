package com.example.moviecatalog.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidateLoginUseCase
import com.example.moviecatalog.common.validation.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepository: AuthRepository,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    fun onLoginChanged(newLogin: String) {
        scope.launch(Dispatchers.IO) {
            val isLoginCorrect = validateLoginUseCase.execute(newLogin)
            val isPasswordCorrect = validatePasswordUseCase.execute(_uiState.value.password)
            val isError = isError(
                newLogin, _uiState.value.password, isLoginCorrect, isPasswordCorrect
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


    fun onPasswordChanged(newPassword: String) {
        scope.launch(Dispatchers.IO) {
            val isLoginCorrect = validatePasswordUseCase.execute(_uiState.value.login)
            val isPasswordCorrect = validatePasswordUseCase.execute(newPassword)
            val isError = isError(
                _uiState.value.login, newPassword, isLoginCorrect, isPasswordCorrect
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

    fun onButtonPressed(navController: NavHostController) {
        scope.launch(Dispatchers.IO) {
            try {
                val user = UserLoginModel(
                    username = _uiState.value.login,
                    password = _uiState.value.password
                )
                val response = authRepository.login(user)

                response.getOrNull()?.token?.let { setTokenToLocalStorageUseCase.execute(it) }

                withContext(Dispatchers.Main) {
                    // навигироваться на MovieScreen
                    navController.navigate(Routes.LaunchScreen.name)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isError = true, isButtonEnabled = false
                    )
                }
            }
        }
    }

    private fun isError(
        login: String, password: String, isLoginCorrect: Boolean, isPasswordCorrect: Boolean
    ): Boolean {
        if (login.isBlank() || password.isBlank()) {
            return false
        }
        return !(isLoginCorrect && isPasswordCorrect)
    }

    private fun isButtonEnabled(login: String, password: String, isError: Boolean): Boolean {
        return login.isNotBlank() && password.isNotBlank() && !isError
    }

    fun onRegisterLinkPressed(navController: NavHostController) {
        navController.navigate(Routes.RegistrationScreen.name)
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