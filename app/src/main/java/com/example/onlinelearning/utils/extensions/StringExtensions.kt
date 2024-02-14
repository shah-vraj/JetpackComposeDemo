package com.example.onlinelearning.utils.extensions

import android.util.Patterns
import java.util.regex.Pattern

private val passwordPattern = Pattern.compile(
    "^" +
            "(?=.*\\d)" +                   // No blank spaces
            "(?=.*[a-z])" +                 // At least 1 lowercase letter
            "(?=.*[A-Z])" +                 // At least 1 uppercase letter
            "(?=.*[!@#\$%^&*])" +           // At least 1 special character
            "[a-zA-Z\\d!@#\$%^&*]{6,}\$"    // At least 6 characters long
)

/**
 * Email validator
 */
fun String.isValidEmailAddress() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Password validator
 */
fun String.isValidPassword() = passwordPattern.matcher(this).matches()