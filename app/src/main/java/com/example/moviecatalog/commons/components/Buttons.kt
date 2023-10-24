package com.example.moviecatalog.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.commons.ui.theme.label15SBTextStyle
import com.example.moviecatalog.commons.ui.theme.text14RTextStyle


@Composable
fun AccentButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    BasicButton(
        text = text,
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
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    BasicButton(
        text = text,
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
    text: String,
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
            text = text,
            style = label15SBTextStyle,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GenderChooseButton(
    selectedGender: Gender,
    onClickMaleButton: () -> Unit,
    onClickFemaleButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.choosing_gender_background),
                shape = RoundedCornerShape(8.dp)
            )

    ) {
        GenderButton(
            text = stringResource(R.string.male),
            isSelected = selectedGender == Gender.Male,
            onClick = onClickMaleButton,
            modifier.weight(1f)
        )
        GenderButton(
            text = stringResource(R.string.female),
            isSelected = selectedGender == Gender.Female,
            onClick = onClickFemaleButton,
            modifier.weight(1f)
        )
    }
}


@Composable
fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColor = if (isSelected) {
        Color.White
    } else {
        Color.Transparent
    }
    val buttonTextColor = if (isSelected) {
        colorResource(R.color.gray_750)
    } else {
        colorResource(R.color.gray_400)
    }
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 2.dp, bottom = 2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = buttonTextColor
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text, style = text14RTextStyle)
    }
}

@Preview
@Composable
fun PreviewGenderChooseButton() {
    GenderChooseButton(
        Gender.Male, {}, {}
    )
}