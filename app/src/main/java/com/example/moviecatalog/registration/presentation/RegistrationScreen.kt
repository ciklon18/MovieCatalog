package com.example.moviecatalog.registration.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.AccentButton
import com.example.moviecatalog.common.ui.component.CustomDateField
import com.example.moviecatalog.common.ui.component.CustomGenderFormField
import com.example.moviecatalog.common.ui.component.CustomPasswordFormField
import com.example.moviecatalog.common.ui.component.CustomTextFormField
import com.example.moviecatalog.common.ui.component.ErrorText
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.ui.component.MyTopAppBar
import com.example.moviecatalog.common.ui.component.PageTitleText
import java.time.LocalDate


@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.isSecondButtonPressed){
        if (uiState.isSecondButtonPressed){
            navController.navigate(Routes.MainScreen.name)
        }
        
    }

    Scaffold(
        topBar = {
            MyTopAppBar(navigateUp = {
                viewModel.onNavigateUpPressed()
                navController.navigateUp()
            })
        }, modifier = modifier
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RegistrationSection(uiState = uiState,
                onNameChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.Name, newValue
                    )
                },
                onGenderChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.Gender, newValue
                    )
                },
                onLoginChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.Login, newValue
                    )
                },
                onEmailChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.Email, newValue
                    )
                },
                onDateChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.BirthDate, newValue
                    )
                },
                onPasswordChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.Password, newValue
                    )
                },
                onRepeatedPasswordChanged = { newValue ->
                    viewModel.onFieldChanged(
                        FieldType.RepeatedPassword, newValue
                    )
                },

                onClickFirstButton = { viewModel.onFirstButtonPressed() },
                onClickSecondButton = {
                    viewModel.onSecondButtonPressed()
                })
            LoginLinkSection(onClickButton = { navController.navigate(Routes.LoginScreen.name) })
        }
    }
}

@Composable
fun RegistrationSection(
    uiState: RegistrationUIState,
    onNameChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    onLoginChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatedPasswordChanged: (String) -> Unit,
    onClickFirstButton: () -> Unit,
    onClickSecondButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PageTitleText(
            text = stringResource(R.string.registration)
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (!uiState.isFirstButtonPressed) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                CustomTextFormField(
                    formText = stringResource(R.string.name),
                    value = uiState.name,
                    onValueChange = onNameChanged,
                    errorMessageResId = uiState.nameErrorMessage
                )
                Spacer(modifier = Modifier.height(15.dp))

                CustomGenderFormField(
                    selectedGender = uiState.gender, onGenderChanged = onGenderChanged
                )
                Spacer(modifier = Modifier.height(15.dp))

                CustomTextFormField(
                    formText = stringResource(R.string.login),
                    value = uiState.login,
                    onValueChange = onLoginChanged,
                    errorMessageResId = uiState.loginErrorMessage

                )
                Spacer(modifier = Modifier.height(15.dp))

                CustomTextFormField(
                    formText = stringResource(R.string.email),
                    value = uiState.email,
                    onValueChange = onEmailChanged,
                    errorMessageResId = uiState.emailErrorMessage
                )
                Spacer(modifier = Modifier.height(15.dp))

                CustomDateField(
                    formText = stringResource(R.string.birth_date),
                    pickedDate = uiState.birthDate,
                    onPickedDateChanged = onDateChanged,
                    errorMessageResId = uiState.birthDateErrorMessage
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            AccentButton(
                text = stringResource(R.string.continue_register),
                onClick = onClickFirstButton,
                isEnabled = uiState.isFirstButtonEnabled,
            )
        } else {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                CustomPasswordFormField(
                    formText = stringResource(R.string.password),
                    value = uiState.password,
                    onValueChange = onPasswordChanged,
                    errorMessageResId = uiState.passwordErrorMessage
                )
                Spacer(modifier = Modifier.height(15.dp))

                CustomPasswordFormField(
                    formText = stringResource(R.string.repeat_password),
                    value = uiState.repeatedPassword,
                    onValueChange = onRepeatedPasswordChanged,
                    errorMessageResId = uiState.repeatedPasswordErrorMessage
                )
            }
            if (uiState.isRegisterError) {
                Spacer(modifier = Modifier.height(8.dp))
                ErrorText(
                    text = stringResource(R.string.registration_error)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            AccentButton(
                text = stringResource(R.string.to_be_registred),
                onClick = onClickSecondButton,
                isEnabled = uiState.isSecondButtonEnabled,
            )
        }


    }
}


@Composable
private fun LoginLinkSection(onClickButton: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(R.string.already_there_is_account))
        Text(text = stringResource(R.string.enter_to_account),
            color = colorResource(R.color.accent),
            modifier = Modifier.clickable { onClickButton() })
    }
}
