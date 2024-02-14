package com.example.onlinelearning.utils.navigation

sealed class Authentication(val route: String) {
    object SignInSignUp : Authentication("signInSignUp")
    object SignIn : Authentication("signIn")
    object SignUp : Authentication("signUp")
    object ForgotPassword : Authentication("forgotPassword")
    object Otp : Authentication("otp")
    object ResetPassword : Authentication("resetPassword")
}
