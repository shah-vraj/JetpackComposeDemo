package com.example.onlinelearning.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val darkColors = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    surface = darkThemeShadowColor
)

private val lightColors = lightColorScheme(
    primary = Color.White,
    onPrimary = BlueText,
    surface = lightThemeShadowColor
)

@Composable
fun OnlineLearningTheme(
    isDarkModeEnabled: Boolean = isSystemInDarkTheme(),
    statusBarColor: Color? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkModeEnabled) darkColors else lightColors
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(statusBarColor ?: colorScheme.primary)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PoppinsTypography,
        content = content
    )
}