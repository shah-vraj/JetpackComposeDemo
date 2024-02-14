package com.example.onlinelearning.ui.authentication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onlinelearning.ui.base.BaseComposeActivity
import com.example.onlinelearning.ui.home.HomeActivity
import com.example.onlinelearning.ui.theme.OnlineLearningTheme
import com.example.onlinelearning.utils.Constants
import com.example.onlinelearning.utils.extensions.startWithIntArgsAndFinish
import com.example.onlinelearning.utils.navigation.Authentication
import com.example.onlinelearning.viewmodel.ForgotAndResetPasswordViewModel
import com.example.onlinelearning.viewmodel.obtainViewModel

class AuthenticationActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineLearningTheme {
                val navHostController = rememberNavController()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    AuthenticationScreens(navHostController) { userId ->
                        startWithIntArgsAndFinish(
                            HomeActivity::class.java,
                            Constants.LOGGED_IN_USER_KEY to userId
                        )
                    }
                }
            }
        }
        setWhiteStatusBar()
    }
}

@Composable
fun AuthenticationScreens(
    navHostController: NavHostController,
    onStartHomeActivity: (Int) -> Unit
) {
    val forgotAndResetPasswordViewModel = obtainViewModel<ForgotAndResetPasswordViewModel>()
    NavHost(
        navController = navHostController,
        startDestination = Authentication.SignInSignUp.route
    ) {
        composable(Authentication.SignInSignUp.route) {
            SignInSignUpScreen(navHostController)
        }
        composable(Authentication.SignIn.route) {
            SignInScreen(navHostController, onStartHomeActivity)
        }
        composable(Authentication.SignUp.route) {
            SignUpScreen(navHostController, onStartHomeActivity)
        }
        composable(Authentication.ForgotPassword.route) {
            ForgotPasswordScreen(navHostController, forgotAndResetPasswordViewModel)
        }
        composable(Authentication.Otp.route) {
            OtpScreen(navHostController, forgotAndResetPasswordViewModel)
        }
        composable(Authentication.ResetPassword.route) {
            ResetPasswordScreen(
                navHostController,
                forgotAndResetPasswordViewModel,
                onStartHomeActivity
            )
        }
    }
}