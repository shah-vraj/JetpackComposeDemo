package com.example.onlinelearning.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.onlinelearning.R
import com.example.onlinelearning.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: SplashScreenViewModel, onStartNewActivity: (Class<*>) -> Unit) {
    LaunchedEffect(true) {
        delay(1000)
        onStartNewActivity(viewModel.destination)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_splash_background),
            contentDescription = "splashBackground",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(R.drawable.ic_splash_logo),
            contentDescription = "splashLogo"
        )
    }
}