package com.example.onlinelearning.utils.extensions

import android.content.Intent
import androidx.activity.ComponentActivity

/**
 * Start new activity by finishing current activity
 */
fun ComponentActivity.startAndFinish(destination: Class<*>) {
    startActivity(Intent(baseContext, destination))
    finish()
}

/**
 * Start new activity by passing few [Int] values and finish current activity
 */
fun ComponentActivity.startWithIntArgsAndFinish(
    destination: Class<*>,
    vararg keyValuePairs: Pair<String, Int>
) {
    val intent = Intent(baseContext, destination)
    keyValuePairs.forEach { intent.putExtra(it.first, it.second) }
    startActivity(intent)
    finish()
}