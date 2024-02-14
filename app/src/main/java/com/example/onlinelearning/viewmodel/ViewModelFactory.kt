package com.example.onlinelearning.viewmodel

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlinelearning.data.local.AppDatabase
import com.example.onlinelearning.utils.prefs.SharedPrefs

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val mSharedPrefs: SharedPrefs,
    private val mAppDatabase: AppDatabase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = with(modelClass) {
        when {
            isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(
                    mApplication,
                    mSharedPrefs
                )
            }
            isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(
                    mSharedPrefs
                )
            }
            isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(
                    mAppDatabase.usersDao(),
                    mSharedPrefs
                )
            }
            isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(
                    mAppDatabase.usersDao(),
                    mSharedPrefs
                )
            }
            isAssignableFrom(ForgotAndResetPasswordViewModel::class.java) -> {
                ForgotAndResetPasswordViewModel(
                    mAppDatabase.usersDao(),
                    mSharedPrefs
                )
            }
            isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(
                    mApplication,
                    mSharedPrefs,
                    mAppDatabase.usersDao()
                )
            }
            else -> {
                throw IllegalArgumentException(
                    "Unknown ViewModel class: ${modelClass.name}"
                )
            }
        }
    } as T

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(Any()) {
                ViewModelFactory(
                    application,
                    SharedPrefs.getInstance(application),
                    AppDatabase.getInstance(application)
                ).also { INSTANCE = it }
            }
    }
}

/**
 * Get the viewmodel for required type [T]
 */
@Composable
inline fun<reified T: ViewModel> obtainViewModel(): T {
    val activity = LocalContext.current as Activity
    return viewModel(factory = ViewModelFactory.getInstance(activity.application))
}