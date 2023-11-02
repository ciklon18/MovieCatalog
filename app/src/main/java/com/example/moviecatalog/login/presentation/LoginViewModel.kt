package com.example.moviecatalog.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.moviecatalog.common.auth.data.mapper.toUserLoginModel
import com.example.moviecatalog.common.auth.domain.usecase.LoginUserUseCase
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.token.domain.usecase.SetTokenToLocalStorageUseCase
import com.example.moviecatalog.common.ui.component.FieldType
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
    private val loginUserUseCase: LoginUserUseCase,
    private val setTokenToLocalStorageUseCase: SetTokenToLocalStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope


    fun onFieldChanged(fieldType: FieldType, newValue: Any) {
        scope.launch(Dispatchers.IO) {
            when (fieldType) {
                FieldType.Login -> onLoginChanged(newLogin = newValue as String)
                FieldType.Password -> onPasswordChanged(newPassword = newValue as String)
                else -> {}
            }
            updateErrorAndButtonStateForField()
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

    fun onButtonPressed(navController: NavHostController) {
        scope.launch(Dispatchers.IO) {
            try {
                val user = _uiState.value.toUserLoginModel()
                val response = loginUserUseCase.execute(user)
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


    fun onRegisterLinkPressed(navController: NavHostController) {
        navController.navigate(Routes.RegistrationScreen.name)
    }
}


data class LoginUIState(
    val login: String = "",
    val loginErrorMessage: Int? = null,
    val password: String = "",
    val passwordErrorMessage: Int? = null,
    val isError: Boolean = false,
    val isButtonEnabled: Boolean = false
)