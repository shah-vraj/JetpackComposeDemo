package com.example.onlinelearning.utils.prefs

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.onlinelearning.utils.Constants.IS_ONBOARDING_COMPLETED_KEY
import com.example.onlinelearning.utils.Constants.IS_USER_LOGGED_IN_KEY
import com.example.onlinelearning.utils.Constants.LOGGED_IN_USER_ID
import com.example.onlinelearning.utils.Constants.SHARED_PREFS_NAME

class SharedPrefs(private val application: Application) {

    private val lazySharedPrefs = lazy {
        application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isOnboardingCompleted by BooleanPreference(
        lazySharedPrefs,
        IS_ONBOARDING_COMPLETED_KEY,
        false
    )
    var isUserLoggedIn by BooleanPreference(
        lazySharedPrefs,
        IS_USER_LOGGED_IN_KEY,
        false
    )
    var loggedInUserId by IntPreference(
        lazySharedPrefs,
        LOGGED_IN_USER_ID,
        UNKNOWN_USER_ID
    )

    companion object {
        const val UNKNOWN_USER_ID = -1
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: SharedPrefs? = null

        fun getInstance(context: Application): SharedPrefs =
            INSTANCE ?: synchronized(Any()) { SharedPrefs(context) }
    }
}