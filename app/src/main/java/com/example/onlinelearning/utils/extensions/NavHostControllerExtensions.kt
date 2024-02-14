package com.example.onlinelearning.utils.extensions

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

/**
 * Navigate to [route] by popping the current backstack
 */
fun NavHostController.navigateWithNoBackStack(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    graph.startDestinationRoute?.let {
        popBackStack(it, true)
    }
    navigate(route, builder)
}