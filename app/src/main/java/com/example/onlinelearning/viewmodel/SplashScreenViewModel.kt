package com.example.onlinelearning.viewmodel

import androidx.lifecycle.ViewModel
import com.example.onlinelearning.ui.authentication.AuthenticationActivity
import com.example.onlinelearning.ui.home.HomeActivity
import com.example.onlinelearning.ui.onboarding.OnboardingActivity
import com.example.onlinelearning.utils.prefs.SharedPrefs

class SplashScreenViewModel(
    sharedPrefs: SharedPrefs
) : ViewModel() {

    val destination = when {
        !sharedPrefs.isOnboardingCompleted -> OnboardingActivity::class.java
        !sharedPrefs.isUserLoggedIn -> AuthenticationActivity::class.java
        else -> HomeActivity::class.java
    }
}