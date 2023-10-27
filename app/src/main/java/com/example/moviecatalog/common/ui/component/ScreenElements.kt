package com.example.moviecatalog.common.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.moviecatalog.R
import com.example.moviecatalog.common.ui.theme.label17SBTextStyle
import com.example.moviecatalog.common.ui.theme.text14RTextStyle
import com.example.moviecatalog.common.ui.theme.title20B2TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
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
            IconButton(
                onClick = navigateUp,
            ) {
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
    )
}

@Composable
fun PageTitleText(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = title20B2TextStyle,
        modifier = modifier
    )
}


@Composable
fun ErrorText(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = text14RTextStyle,
        color = colorResource(R.color.light_accent),
        modifier = modifier
    )
}