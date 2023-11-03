package com.example.moviecatalog.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.moviecatalog.common.auth.domain.model.UserLoginModel
import com.example.moviecatalog.common.auth.domain.repository.AuthRepository
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.validation.domain.usecase.LoginValidationUseCase
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
    private val loginValidationUseCase: LoginValidationUseCase,
    private val authRepository: AuthRepository,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope

    fun onLoginChanged(newLogin: String) {
        scope.launch(Dispatchers.IO) {
            val isLoginCorrect = loginValidationUseCase.validateLogin(newLogin)
            val isPasswordCorrect = loginValidationUseCase.validatePassword(_uiState.value.password)
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
            val isLoginCorrect = loginValidationUseCase.validateLogin(_uiState.value.login)
            val isPasswordCorrect = loginValidationUseCase.validatePassword(newPassword)
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
                val token = response.getOrNull()?.token
                token?.let { setTokenToLocalStorageUseCase.execute(it) }
                if (token != null) {
                    withContext(Dispatchers.Main) {
                        // навигироваться на MovieScreen
                        navController.navigate(Routes.ProfileScreen.name)
                    }
                } else {
                    handleException()
                }


                // обработать response
            } catch (e: Exception) {
                handleException()
            }
        }
    }

    private fun handleException() {
        scope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    isError = true, isButtonEnabled = false
                )
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