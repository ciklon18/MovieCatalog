package com.example.moviecatalog.common.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.common.ui.theme.label14MTextStyle
import com.example.moviecatalog.common.ui.theme.label15SBTextStyle
import com.example.moviecatalog.common.ui.theme.text14RTextStyle


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
            contentColor = colorResource(R.color.white),
            disabledContainerColor = colorResource(R.color.dismiss_accent),

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
fun TransparentButton(
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
            containerColor = Color.Transparent,
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
            ),
    ) {
        GenderButton(
            text = stringResource(R.string.male),
            isSelected = selectedGender == Gender.Male,
            onClick = onClickMaleButton,
            modifier = modifier.weight(1f)
        )
        GenderButton(
            text = stringResource(R.string.female),
            isSelected = selectedGender == Gender.Female,
            onClick = onClickFemaleButton,
            modifier = modifier.weight(1f)
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
        modifier = modifier,
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
    var gender by remember { mutableStateOf(Gender.Male) }
    GenderChooseButton(
        gender, { gender = Gender.Male }, { gender = Gender.Female }
    )
}


@Composable
fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .width(40.dp)
            .height(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(R.color.gray_750),
            contentColor = colorResource(if (isFavorite) R.color.accent else R.color.white)
        )

    ) {
        Icon(
            painter = painterResource(if (isFavorite) R.drawable.filled_favorite else R.drawable.unfilled_favorite),
            contentDescription = stringResource(R.string.favorite_button),
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
            tint = colorResource(if (isFavorite) R.color.accent else R.color.white)

        )
    }
}


@Composable
fun AddReviewButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .width(32.dp)
            .height(32.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(R.color.accent),
            contentColor = colorResource(R.color.white)
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.add),
            contentDescription = stringResource(R.string.add_review),
            tint = colorResource(R.color.white),
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
        )
    }
}


@Composable
fun MoreDetailsButton(
    areMoreDetailsShowed: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (areMoreDetailsShowed) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )
    Row(
        modifier = modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.more),
            color = colorResource(R.color.accent),
            style = label14MTextStyle
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = stringResource(R.string.more),
            tint = colorResource(R.color.accent),
            modifier = Modifier.rotate(rotationAngle)
        )
    }
}


@Composable
fun ReviewManagementButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .width(26.dp)
            .height(26.dp)
            .clip(RoundedCornerShape(30.dp)),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(R.color.gray_750),
            contentColor = colorResource(R.color.white)
        ),
    ) {
        Icon(
            painter = painterResource(R.drawable.three_dots),
            contentDescription = stringResource(R.string.manage_review),
            tint = colorResource(R.color.white),
            modifier = Modifier
                .height(20.dp)
                .width(20.dp),
        )
    }
}


@Composable
fun ReviewButtons(
    isAccentButtonEnabled: Boolean,
    onSaveClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        AccentButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            isEnabled = isAccentButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        SecondaryButton(
            text = stringResource(R.string.dismiss),
            onClick = onDismissClick,
            isEnabled = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}