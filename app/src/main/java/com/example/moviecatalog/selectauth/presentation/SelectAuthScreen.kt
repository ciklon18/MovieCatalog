package com.example.moviecatalog.selectauth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.navigation.Routes
import com.example.moviecatalog.common.ui.component.AccentButton
import com.example.moviecatalog.common.ui.component.SecondaryButton
import com.example.moviecatalog.common.ui.theme.text15RTextStyle
import com.example.moviecatalog.common.ui.theme.title20B2TextStyle

@Composable
fun SelectAuthScreen(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Image(
            painter = painterResource(R.drawable.select_auth_image),
            contentDescription = stringResource(R.string.select_auth_image_description)
        )
        Spacer(modifier = modifier.height(35.dp))
        Text(
            text = stringResource(R.string.select_auth_title),
            color = colorResource(R.color.white),
            style = title20B2TextStyle
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = stringResource(R.string.select_auth_text),
            color = colorResource(R.color.white),
            style = text15RTextStyle
        )
        Spacer(modifier = modifier.height(35.dp))

        AccentButton(
            text = stringResource(R.string.registration),
            onClick = { navController.navigate(Routes.RegistrationScreen.name) },
            isEnabled = true
        )
        Spacer(modifier = modifier.height(15.dp))
        SecondaryButton(
            text = stringResource(R.string.entrance),
            onClick = { navController.navigate(Routes.LoginScreen.name) },
            isEnabled = true
        )
    }
}

