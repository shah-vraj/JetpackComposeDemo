package com.example.onlinelearning.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp

/**
 * Show toast with given [message] in non composable functions
 */
fun Context.showShortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

/**
 * Show toast with given [message] in composable functions
 */
@Composable
fun ShowShortToast(message: String) =
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()

@Composable
fun Float.toDp(): Dp =
    Dp(this@toDp / LocalContext.current.resources.displayMetrics.density)