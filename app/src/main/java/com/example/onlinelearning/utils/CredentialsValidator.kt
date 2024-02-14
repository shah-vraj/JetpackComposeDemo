package com.example.onlinelearning.utils

/**
 * Represents end results on validating credentials
 */
sealed class CredentialsValidator {
    object EmptyCredentials : CredentialsValidator()
    object InvalidEmailAddress : CredentialsValidator()
    object InvalidPassword : CredentialsValidator()
    object UserAlreadyExists : CredentialsValidator()
    object UserNotFound : CredentialsValidator()
    object WrongPassword : CredentialsValidator()
    class Success(val id: Int) : CredentialsValidator()
}