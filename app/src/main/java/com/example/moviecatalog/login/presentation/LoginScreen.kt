package com.example.moviecatalog.login.presentation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.AccentButton
import com.example.moviecatalog.common.ui.component.CustomPasswordFormField
import com.example.moviecatalog.common.ui.component.CustomTextFormField
import com.example.moviecatalog.common.ui.component.ErrorText
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.ui.component.MyTopAppBar
import com.example.moviecatalog.common.ui.component.PageTitleText

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isButtonPressed){
        if (uiState.isButtonPressed){
            navController.navigate(Routes.MainScreen.name)
        }
    }


    Scaffold(
        topBar = { MyTopAppBar(navigateUp = { navController.navigateUp() }) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LoginSection(updatedLogin = uiState.login,
                updatedPassword = uiState.password,
                uiState = uiState,
                onLoginChanged = { newLogin ->
                    viewModel.onFieldChanged(
                        FieldType.Login,
                        newLogin
                    )
                },
                onPasswordChanged = { newPassword ->
                    viewModel.onFieldChanged(
                        FieldType.Password,
                        newPassword
                    )
                },
                onClickButton = {
                    viewModel.onButtonPressed()
                }
            )
            RegistrationLinkSection(
                onClickButton = { viewModel.onRegisterLinkPressed(navController) }
            )
        }
    }
}

@Composable
private fun LoginSection(
    updatedLogin: String,
    updatedPassword: String,
    uiState: LoginUIState,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PageTitleText(
            text = stringResource(R.string.entrance)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            CustomTextFormField(
                formText = stringResource(R.string.login),
                value = updatedLogin,
                onValueChange = onLoginChanged,
                errorMessageResId = uiState.loginErrorMessage
            )
            Spacer(modifier = Modifier.height(15.dp))

            CustomPasswordFormField(
                formText = stringResource(R.string.password),
                value = updatedPassword,
                onValueChange = onPasswordChanged,
                errorMessageResId = uiState.passwordErrorMessage
            )
            if (uiState.isError) {
                Spacer(modifier = Modifier.height(8.dp))
                ErrorText(
                    text = stringResource(R.string.wrong_login_or_password)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        AccentButton(
            text = stringResource(R.string.enter),
            onClick = onClickButton,
            isEnabled = uiState.isButtonEnabled,
        )
    }
}

@Composable
private fun RegistrationLinkSection(onClickButton: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(R.string.is_there_account))
        Text(
            text = stringResource(R.string.register),
            color = colorResource(R.color.accent),
            modifier = Modifier.clickable { onClickButton() }
        )
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        rememberNavController(), hiltViewModel()
    )
}

