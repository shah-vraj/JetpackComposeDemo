package com.example.onlinelearning.utils.extensions

import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager

/**
 * Focus manager extension to move focus left
 */
fun FocusManager.moveLeft() = moveFocus(FocusDirection.Left)

/**
 * Focus manager extension to move focus right
 */
fun FocusManager.moveRight() = moveFocus(FocusDirection.Right)