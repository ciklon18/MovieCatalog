package com.example.moviecatalog.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.label15SBTextStyle
import com.example.moviecatalog.ui.theme.text15RTextStyle
import com.example.moviecatalog.ui.theme.title20B2TextStyle

@Composable
fun SelectAuthScreen(
    modifier: Modifier = Modifier
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
            stringRes = R.string.registration,
            onClick = { /*TODO*/ },
            isEnabled = true
        )
        Spacer(modifier = modifier.height(15.dp))
        SecondaryButton(
            stringRes = R.string.entrance,
            onClick = { /*TODO*/ },
            isEnabled = true
        )
    }
}


@Composable
fun AccentButton(
    @StringRes stringRes: Int,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    BasicButton(
        stringRes = stringRes,
        onClick = onClick,
        isEnabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.accent),
            contentColor = colorResource(R.color.white)
        ),
        modifier = modifier
    )
}

@Composable
fun SecondaryButton(
    @StringRes stringRes: Int,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    BasicButton(
        stringRes = stringRes,
        onClick = onClick,
        isEnabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.gray_800),
            contentColor = colorResource(R.color.accent)
        ),
        modifier = modifier
    )
}

@Composable
fun BasicButton(
    @StringRes stringRes: Int,
    onClick: () -> Unit,
    isEnabled: Boolean,
    colors: ButtonColors,
    modifier: Modifier = Modifier
){
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

@Preview
@Composable
fun PreviewAccentButton() {
    AccentButton(stringRes = R.string.registration, onClick = { /*TODO*/ }, isEnabled = true)
}