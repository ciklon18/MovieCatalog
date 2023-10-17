package com.example.moviecatalog.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.label15SBTextStyle


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
