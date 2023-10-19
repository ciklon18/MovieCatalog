package com.example.moviecatalog.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecatalog.R
import com.example.moviecatalog.components.BasicButton
import com.example.moviecatalog.components.CustomPasswordTextField
import com.example.moviecatalog.components.CustomTextField
import com.example.moviecatalog.ui.theme.label15MTextStyle
import com.example.moviecatalog.ui.theme.label17SBTextStyle
import com.example.moviecatalog.ui.theme.text14RTextStyle
import com.example.moviecatalog.ui.theme.title20B2TextStyle
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var updatedLogin by remember { mutableStateOf(uiState.login) }
    var updatedPassword by remember { mutableStateOf(uiState.password) }

    LaunchedEffect(updatedLogin) {
        delay(300)
        viewModel.onLoginChanged(updatedLogin)
    }

    LaunchedEffect(updatedPassword) {
        delay(300)
        viewModel.onPasswordChanged(updatedPassword)
    }

    Scaffold(
        topBar = {
            LoginTopAppBar(navigateUp = { navController.navigateUp() })
        },
        modifier = modifier
            .padding(16.dp)
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.entrance),
                style = title20B2TextStyle,
                modifier = Modifier.weight(0.05f)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(0.3f)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = label15MTextStyle
                )
                Spacer(modifier = Modifier.weight(0.1f))

                CustomTextField(
                    value = updatedLogin,
                    onValueChange = { newLogin ->
                        updatedLogin = newLogin
                    },
                    isError = uiState.isLoginWrong,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.15f))
                Text(
                    text = stringResource(R.string.password),
                    style = label15MTextStyle
                )
                Spacer(modifier = Modifier.weight(0.1f))
                CustomPasswordTextField(
                    value = updatedPassword,
                    onValueChange = { newPassword ->
                        updatedPassword = newPassword
                    },
                    isError = uiState.isPasswordWrong,
                    modifier = Modifier.weight(1f)
                )

                if (uiState.isLoginWrong || uiState.isPasswordWrong) {
                    Text(
                        text = stringResource(R.string.wrong_login_or_password),
                        style = text14RTextStyle,
                        color = colorResource(R.color.light_accent)
                    )
                }
                Spacer(modifier = Modifier.weight(0.35f))
                BasicButton(
                    stringRes = R.string.entrance,
                    onClick = { /*TODO*/ },
                    isEnabled = uiState.isButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.accent),
                        contentColor = colorResource(R.color.white)
                    ),
                    modifier = Modifier.weight(1f)
                )


            }
            Spacer(modifier = Modifier.weight(0.55f))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(0.05f),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.is_there_account))
                Text(
                    text = stringResource(R.string.register),
                    color = colorResource(R.color.accent),
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTopAppBar(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.top_app_text),
                style = label17SBTextStyle
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = colorResource(R.color.accent),
            navigationIconContentColor = colorResource(R.color.white)
        ),
        modifier = modifier
            .background(Color.Red)
    )
}

@Preview
@Composable
fun PreviewCustomTextField() {
    CustomTextField(value = "", onValueChange = {}, isError = false)
}