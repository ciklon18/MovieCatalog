package com.example.moviecatalog.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.AccentButton
import com.example.moviecatalog.common.ui.component.CustomDateField
import com.example.moviecatalog.common.ui.component.CustomGenderFormField
import com.example.moviecatalog.common.ui.component.CustomTextFormField
import com.example.moviecatalog.common.ui.component.FieldType
import com.example.moviecatalog.common.ui.component.Gender
import com.example.moviecatalog.common.ui.component.MyBottomBar
import com.example.moviecatalog.common.ui.component.MyProfileCard
import com.example.moviecatalog.common.ui.component.MyTab
import com.example.moviecatalog.common.ui.component.SecondaryButton
import com.example.moviecatalog.common.ui.component.TransparentButton
import java.time.LocalDate


@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel  = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLogout){
        if (uiState.isLogout){
            navController.navigate(Routes.SelectAuthScreen.name){
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        bottomBar = {
            MyBottomBar(
                onMainClicked = { navController.navigate(Routes.MainScreen.name) {
                    navController.popBackStack()
                } },

                onFavoriteClicked = { navController.navigate(Routes.FavoriteScreen.name) {
                    navController.popBackStack()
                } },
                onProfileClicked = { navController.navigate(Routes.ProfileScreen.name) {
                    navController.popBackStack()
                } },
                myTab = MyTab.Profile
            )
        }, modifier = modifier
    ) { innerPadding ->
        FieldsSection(viewModel, uiState, innerPadding)
    }
}
@Composable
private fun FieldsSection(viewModel: ProfileViewModel, uiState: ProfileUIState, innerPadding: PaddingValues){
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                MyProfileCard(
                    link = uiState.avatarLink,
                    nickname = uiState.nickName,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                TransparentButton(
                    text = stringResource(R.string.logout),
                    onClick = { viewModel.onLogoutButtonPressed() },
                    isEnabled = true
                )
            }
        }
        item {
            FieldsSection(uiState = uiState, onEmailChanged = { newValue ->
                viewModel.onFieldChanged(FieldType.Email, newValue)
            }, onBirthDateChanged = { newValue ->
                viewModel.onFieldChanged(FieldType.BirthDate, newValue)
            }, onGenderChanged = { newValue ->
                viewModel.onFieldChanged(FieldType.Gender, newValue)
            }, onLinkChanged = { newValue ->
                viewModel.onFieldChanged(FieldType.Link, newValue)
            }, onNameChanged = { newValue ->
                viewModel.onFieldChanged(FieldType.Name, newValue)
            }, modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }
        item {
            ButtonSection(
                onClickSaveButton = { viewModel.onSaveButtonPressed() },
                isSaveButtonEnabled = uiState.isSaveButtonEnabled,
                onClickDismissButton = { viewModel.onCancelButtonPressed() },
                isDismissButtonEnabled = uiState.isDismissButtonEnabled,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun FieldsSection(
    uiState: ProfileUIState,
    onEmailChanged: (String) -> Unit,
    onLinkChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onGenderChanged: (Gender) -> Unit,
    onBirthDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically)
    ) {
        CustomTextFormField(
            formText = stringResource(R.string.email),
            value = uiState.email,
            onValueChange = onEmailChanged,
            errorMessageResId = uiState.emailErrorMessage

        )
        CustomTextFormField(
            formText = stringResource(R.string.link_to_avatar),
            value = uiState.avatarLink,
            onValueChange = onLinkChanged,
            errorMessageResId = uiState.emailErrorMessage
        )
        CustomTextFormField(
            formText = stringResource(R.string.name),
            value = uiState.name,
            onValueChange = onNameChanged,
            errorMessageResId = uiState.emailErrorMessage
        )
        CustomGenderFormField(
            selectedGender = uiState.gender, onGenderChanged = onGenderChanged
        )
        CustomDateField(
            formText = stringResource(R.string.birth_date),
            pickedDate = uiState.birthDate,
            errorMessageResId = uiState.emailErrorMessage,
            onPickedDateChanged = onBirthDateChanged
        )
    }

}

@Composable
private fun ButtonSection(
    isSaveButtonEnabled: Boolean,
    isDismissButtonEnabled: Boolean,
    onClickSaveButton: () -> Unit,
    onClickDismissButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically)
    ) {
        AccentButton(
            text = stringResource(R.string.save),
            onClick = onClickSaveButton,
            isEnabled = isSaveButtonEnabled
        )
        SecondaryButton(
            text = stringResource(R.string.dismiss),
            onClick = onClickDismissButton,
            isEnabled = isDismissButtonEnabled
        )
    }
}
