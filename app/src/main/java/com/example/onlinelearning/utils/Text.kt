package com.example.onlinelearning.utils

import androidx.annotation.StringRes

/**
 * Represents text
 */
sealed interface Text {
    /**
     * Represents text taken directly from [text]
     */
    data class String(val text: kotlin.String) : Text

    /**
     * Represents text taken from resource with id [id]
     */
    data class StringResource(@StringRes val id: Int) : Text
}