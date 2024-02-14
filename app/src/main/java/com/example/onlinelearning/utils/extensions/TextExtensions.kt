package com.example.onlinelearning.utils.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.onlinelearning.utils.Text

/**
 * Get the text from non composable function
 */
fun Text.getText(context: Context): String = when (this) {
    is Text.String -> text
    is Text.StringResource -> context.getString(id)
}

/**
 * Get the text from composable function
 */
@Composable
fun Text.getText(): String = when (this) {
    is Text.String -> text
    is Text.StringResource -> LocalContext.current.getString(id)
}