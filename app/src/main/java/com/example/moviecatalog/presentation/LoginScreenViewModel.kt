package com.example.moviecatalog.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
//    private val validateLoginUseCase:  ValidateLoginUseCase,
//    private val validatePasswordUseCase: ValidateLoginUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow() // уточнить тип флоу


}



data class LoginUIState(
    val login: String = "",
    val password:  String = "",

)