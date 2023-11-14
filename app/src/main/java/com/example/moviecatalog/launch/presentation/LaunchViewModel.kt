package com.example.moviecatalog.launch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.profile.domain.usecase.DeleteProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.DeleteTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.IsTokenExpiredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val isTokenExpiredUseCase: IsTokenExpiredUseCase,
    private val deleteTokenFromLocalStorageUseCase: DeleteTokenFromLocalStorageUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val setProfileToLocalStorageUseCase: SetProfileToLocalStorageUseCase,
    private val deleteProfileFromLocalStorageUseCase: DeleteProfileFromLocalStorageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaunchUIState())
    val uiState: StateFlow<LaunchUIState> = _uiState.asStateFlow()

    init {
        checkTokenValid()
        updateUserProfile()
    }

    private fun updateUserProfile() {
        viewModelScope.launch(Dispatchers.Default) {
            try{
                if (!_uiState.value.isTokenExpired){
                    val result = getProfileUseCase.execute(_uiState.value.token)
                    val profile = result.getOrNull()
                    if (result.isSuccess && profile != null){
                        setProfileToLocalStorageUseCase.execute(profile)
                    } else{
                        deleteProfileFromLocalStorageUseCase.execute()
                    }

                }
            } catch (e: Exception){
                deleteProfileFromLocalStorageUseCase.execute()
                deleteTokenFromLocalStorageUseCase.execute()
            }

        }
    }

    private fun checkTokenValid() {
        viewModelScope.launch(Dispatchers.Default) {
            val token = getTokenFromLocalStorageUseCase.execute()
            val isTokenExpired = isTokenExpiredUseCase.execute(token)
            _uiState.update { currentState ->
                currentState.copy(
                    token = if (isTokenExpired) "" else token,
                    isTokenExpired = isTokenExpired
                )
            }
            if (isTokenExpired) {
                deleteTokenFromLocalStorageUseCase.execute()
            }
        }

    }
}

data class LaunchUIState(
    val token: String = "",
    val isTokenExpired: Boolean = true
)