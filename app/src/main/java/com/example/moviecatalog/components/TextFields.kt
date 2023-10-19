package com.example.moviecatalog.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R


@Composable
fun CustomTextField(
    value: String, onValueChange: (String) -> Unit, isError: Boolean, modifier: Modifier = Modifier
) {
    val containerColor = if (!isError) Color.Transparent else colorResource(R.color.dark_accent)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Go
        ),
        isError = isError,
        keyboardActions = KeyboardActions(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedBorderColor = colorResource(R.color.border_color),
            unfocusedBorderColor = colorResource(R.color.border_color),
            errorBorderColor = colorResource(R.color.accent),
        ),
    )
}

@Composable
fun CustomPasswordTextField(
    value: String, onValueChange: (String) -> Unit, isError: Boolean, modifier: Modifier = Modifier
) {
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
        ),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                painterResource(R.drawable.show_password)
            } else {
                painterResource(R.drawable.hide_password)
            }

            val imageDescription = if (passwordVisible.value) {
                stringResource(R.string.show_password)
            } else {
                stringResource(R.string.hide_password)
            }
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = iconImage, contentDescription = imageDescription)
            }

        },

        isError = isError,
        keyboardActions = KeyboardActions(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.border_color),
            unfocusedBorderColor = colorResource(R.color.border_color),
            errorBorderColor = colorResource(R.color.accent),
        ),
    )
}

