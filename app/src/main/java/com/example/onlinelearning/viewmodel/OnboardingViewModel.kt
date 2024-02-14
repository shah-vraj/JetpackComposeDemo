package com.example.onlinelearning.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.onlinelearning.data.model.OnboardingData
import com.example.onlinelearning.utils.prefs.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class OnboardingViewModel(
    private val context: Application,
    private val sharedPrefs: SharedPrefs
): ViewModel() {

    private val mCurrentPage = MutableStateFlow(0)
    val currentPage = mCurrentPage.asStateFlow()

    val data = OnboardingData.getData(context)
    val isUserLoggedIn = sharedPrefs.isUserLoggedIn

    val currentSweepAngle = currentPage.map {
        (120 * (it + 1)).toFloat()
    }
    val topBarRightButtonText = currentPage.map {
        when (it) {
            in 0..data.size - 2 -> "Skip"
            data.size - 1 -> "Get Started"
            else -> ""
        }
    }

    fun onPageChange(page: Int) {
        mCurrentPage.value = page
    }

    fun setOnboardingCompleted() {
        sharedPrefs.isOnboardingCompleted = true
    }
}