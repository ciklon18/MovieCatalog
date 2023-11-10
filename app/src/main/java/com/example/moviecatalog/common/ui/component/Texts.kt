package com.example.moviecatalog.common.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.example.moviecatalog.R
import com.example.moviecatalog.common.ui.theme.label14MTextStyle
import com.example.moviecatalog.common.ui.theme.label16BTextStyle
import com.example.moviecatalog.common.ui.theme.text14RTextStyle
import com.example.moviecatalog.common.ui.theme.title24BTextStyle

@Composable
fun MovieDescriptionText(
    areMoreDetailsShowed: Boolean, description: String, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = description,
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(),
            style = label14MTextStyle,
            maxLines = if (!areMoreDetailsShowed) 4 else Int.MAX_VALUE,
            textAlign = TextAlign.Start
        )
        AnimatedVisibility(
            visible = !areMoreDetailsShowed,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300)),
            modifier = Modifier.matchParentSize()
        ) {
            MovieGradientElement(modifier = Modifier.fillMaxSize())
        }
    }
}


@Composable
fun StyledDetailsText(value: String, modifier: Modifier = Modifier) {
    Text(
        text = value,
        color = colorResource(R.color.gray_400),
        style = text14RTextStyle,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

@Composable
fun BasicDetailsText(value: String, modifier: Modifier = Modifier) {
    Text(
        text = value,
        color = colorResource(R.color.white),
        style = text14RTextStyle,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}


@Composable
fun MovieSubtitleText(text: String) {
    Text(
        text = text,
        color = colorResource(R.color.white),
        style = label16BTextStyle
    )
}

@Composable
fun MovieNameText(text: String) {
    Text(
        text = text, color = colorResource(R.color.white), style = title24BTextStyle
    )
}


@Composable
fun MovieNameTextFromMain(
    name: String?,
    modifier: Modifier = Modifier
){
    Text(
        text = name ?: "",
        color = colorResource(R.color.white),
        style = label16BTextStyle,
        modifier = modifier
    )
}
