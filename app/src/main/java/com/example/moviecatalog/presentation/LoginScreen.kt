package com.example.moviecatalog.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.label15MTextStyle
import com.example.moviecatalog.ui.theme.label15SBTextStyle
import com.example.moviecatalog.ui.theme.label17SBTextStyle
import com.example.moviecatalog.ui.theme.title20B2TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            LoginTopAppBar(onClick = {
            })
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
                modifier = Modifier.weight(0.35f)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = label15MTextStyle
                )
                Spacer(modifier = Modifier.weight(0.1f))

                CustomTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.15f))
                Text(
                    text = stringResource(R.string.password),
                    style = label15MTextStyle
                )
                Spacer(modifier = Modifier.weight(0.1f))
                CustomTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.25f))
                BasicButton(
                    stringRes = R.string.entrance,
                    onClick = { /*TODO*/ },
                    isEnabled = true,
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

@Composable
fun BasicButton(
    @StringRes stringRes: Int,
    onClick: () -> Unit,
    isEnabled: Boolean,
    colors: ButtonColors,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        colors = colors,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(stringRes),
            style = label15SBTextStyle
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTopAppBar(
    onClick: () -> Unit,
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
            IconButton(onClick = onClick) {
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

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(R.color.border_color),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        singleLine = true
    )
}


@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(LoginScreenViewModel())
}

@Preview
@Composable
fun PreviewCustomTextField() {
    CustomTextField(value = "", onValueChange = {})
}