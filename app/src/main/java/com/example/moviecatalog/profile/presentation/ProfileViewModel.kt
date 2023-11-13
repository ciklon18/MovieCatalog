package com.example.moviecatalog.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.moviecatalog.common.auth.domain.usecase.LogoutUserUseCase
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.profile.data.mapper.toProfile
import com.example.moviecatalog.common.profile.data.mapper.toProfileUIState
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileFromLocalStorageUseCase
import com.example.moviecatalog.common.profile.domain.usecase.GetProfileUseCase
import com.example.moviecatalog.common.profile.domain.usecase.SetProfileToLocalStorageUseCase
import com.example.moviecatalog.common.profile.domain.usecase.UpdateProfileUseCase
import com.example.moviecatalog.common.token.domain.usecase.DeleteTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.token.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.validation.domain.usecase.ProfileValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfileFromLocalStorageUseCase: GetProfileFromLocalStorageUseCase,
    private val profileValidationUseCase: ProfileValidationUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val setProfileToLocalStorageUseCase: SetProfileToLocalStorageUseCase,
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val deleteTokenFromLocalStorageUseCase: DeleteTokenFromLocalStorageUseCase
) : ViewModel() {
    private val oldProfileData = MutableStateFlow(ProfileUIState())
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    private val scope = viewModelScope


    init {
        initProfileData()
    }

    private fun initProfileData() {
        scope.launch(Dispatchers.Default) {
            val token = getTokenFromLocalStorageUseCase.execute()
            try {
                val result = getProfileFromLocalStorageUseCase.execute()

                if (result.isSuccess) {
                    val profile = result.getOrNull()
                    if (profile != null) {
                        val uiState = profile.toProfileUIState()
                        updateUiStatesData(uiState, token)
                    }
                }
            } catch (e: Exception){
                val result = getProfileUseCase.execute(token)

                if (result.isSuccess) {
                    val profile = result.getOrNull()
                    if (profile != null) {
                        val uiState = profile.toProfileUIState()
                        updateUiStatesData(uiState, token)
                    }
                }
            }


        }
    }

    private fun updateUiStatesData(uiState: ProfileUIState, token: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = uiState.email,
                avatarLink = uiState.avatarLink,
                name = uiState.name,
                birthDate = uiState.birthDate,
                gender = uiState.gender,
                token = token
            )
        }
        oldProfileData.update { currentState ->
            currentState.copy(
                email = uiState.email,
                avatarLink = uiState.avatarLink,
                name = uiState.name,
                birthDate = uiState.birthDate,
                gender = uiState.gender,
                token = token
            )
        }
    }


    fun onLogoutButtonPressed(navController: NavHostController) {
        scope.launch(Dispatchers.Default) {
            logoutUserUseCase.execute()
            deleteTokenFromLocalStorageUseCase.execute()
            withContext(Dispatchers.Main) {
                navController.navigate(Routes.SelectAuthScreen.name)
            }
        }
    }

    fun onFieldChanged(fieldType: FieldType, newValue: Any) {
        scope.launch(Dispatchers.Default) {
            when (fieldType) {
                FieldType.Email -> onEmailChanged(newEmail = newValue as String)
                FieldType.Link -> onLinkChanged(newLink = newValue as String)
                FieldType.Name -> onNameChanged(newName = newValue as String)
                FieldType.Gender -> onGenderChanged(newGender = newValue as Gender)
                FieldType.BirthDate -> onBirthDateChanged(newBirthDate = newValue as LocalDate)
                else -> {}
            }
            if (hasFieldChanged(fieldType, newValue)) {
                updateErrorAndButtonStateForField()
            }
        }
    }

    private fun hasFieldChanged(fieldType: FieldType, newValue: Any): Boolean {
        return when (fieldType) {
            FieldType.Email -> newValue != oldProfileData.value.email
            FieldType.Link -> newValue != oldProfileData.value.avatarLink
            FieldType.Name -> newValue != oldProfileData.value.name
            FieldType.Gender -> newValue != oldProfileData.value.gender
            FieldType.BirthDate -> newValue != oldProfileData.value.birthDate
            else -> false
        }
    }

    private fun updateErrorAndButtonStateForField() {
        _uiState.update { currentState ->
            val isUpdatedFieldCorrect = profileValidationUseCase.isDataValid(
                _uiState.value.emailErrorMessage,
                _uiState.value.linkErrorMessage,
                _uiState.value.nameErrorMessage,
                _uiState.value.birthDateErrorMessage
            )
            currentState.copy(
                isSaveButtonEnabled = isUpdatedFieldCorrect
            )
        }
    }



    private fun onEmailChanged(newEmail: String) {
        val isEmailCorrect = profileValidationUseCase.validateEmail(newEmail)
        _uiState.update { currentState ->
            currentState.copy(
                email = newEmail,
                emailErrorMessage = isEmailCorrect.errorMessage,
                isDismissButtonEnabled = true
            )
        }
    }

    private fun onLinkChanged(newLink: String) {
        val isLinkCorrect = profileValidationUseCase.validateLink(newLink)
        _uiState.update { currentState ->
            currentState.copy(
                avatarLink = newLink,
                linkErrorMessage = isLinkCorrect.errorMessage,
                isDismissButtonEnabled = true
            )
        }
    }


    private fun onNameChanged(newName: String) {
        val isNameCorrect = profileValidationUseCase.validateName(newName)
        _uiState.update { currentState ->
            currentState.copy(
                name = newName,
                nameErrorMessage = isNameCorrect.errorMessage,
                isDismissButtonEnabled = true
            )
        }
    }

    private fun onGenderChanged(newGender: Gender) {
        _uiState.update { currentState ->
            currentState.copy(
                gender = newGender, isDismissButtonEnabled = true
            )
        }
    }

    private fun onBirthDateChanged(newBirthDate: LocalDate) {
        val isBirthDateCorrect = profileValidationUseCase.validateDate(newBirthDate)
        _uiState.update { currentState ->
            currentState.copy(
                birthDate = newBirthDate,
                birthDateErrorMessage = isBirthDateCorrect.errorMessage,
                isDismissButtonEnabled = true
            )
        }
    }


    fun onSaveButtonPressed() {
        scope.launch(Dispatchers.Default) {
            val id = UUID.randomUUID().toString()
            val profile = _uiState.value.toProfile().copy(id = id)
            val networkResult = updateProfileUseCase.execute(_uiState.value.token, profile)

            if (networkResult.isSuccess) {
                setProfileToLocalStorageUseCase.execute(profile)
                oldProfileData.value = _uiState.value
            } else {
                _uiState.value = oldProfileData.value
            }
            _uiState.value = _uiState.value.copy(isSaveButtonEnabled = false)
        }
    }

    fun onCancelButtonPressed() {
        scope.launch(Dispatchers.Default) {
            _uiState.value = oldProfileData.value
        }
    }
}


data class ProfileUIState(
    val id: String = "",
    val nickName: String = "",
    val email: String = "",
    val emailErrorMessage: Int? = null,
    val avatarLink: String = "",
    val linkErrorMessage: Int? = null,
    val name: String = "",
    val nameErrorMessage: Int? = null,
    val birthDate: LocalDate? = null,
    val birthDateErrorMessage: Int? = null,
    val gender: Gender = Gender.Male,
    val isSaveButtonEnabled: Boolean = false,
    val isDismissButtonEnabled: Boolean = false,
    val token: String = ""
)