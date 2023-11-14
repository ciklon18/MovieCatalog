package com.example.moviecatalog.launch.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.navigation.Routes

@Composable
fun LaunchScreen(
    navController: NavHostController,
    viewModel: LaunchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(Unit) {
        alpha.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )

        if (uiState.isTokenExpired) {
            navController.navigate(Routes.SelectAuthScreen.name){
                navController.popBackStack()
            }
        } else {
            navController.navigate(Routes.MainScreen.name) {
                navController.popBackStack()
            }
        }

    }
    Image(
        painter = painterResource(R.drawable.launch_screen_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha.value),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.dark_logo),
            contentDescription = null
        )
    }
}