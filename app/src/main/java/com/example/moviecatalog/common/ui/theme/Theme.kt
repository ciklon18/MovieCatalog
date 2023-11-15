package com.example.moviecatalog.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)


@Composable
fun MovieCatalogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            dynamicDarkColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        if (darkTheme) {
            systemUiController.setSystemBarsColor(
                color = Color.Black,
                darkIcons = false
            )
        } else {
            systemUiController.setSystemBarsColor(
                color = Color.White,
                darkIcons = true
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}