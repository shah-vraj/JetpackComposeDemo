package com.example.onlinelearning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinelearning.data.local.dao.UsersDao
import com.example.onlinelearning.data.local.entity.UserEntity
import com.example.onlinelearning.data.local.repository.UserRepository
import com.example.onlinelearning.utils.CredentialsValidator
import com.example.onlinelearning.utils.extensions.isValidEmailAddress
import com.example.onlinelearning.utils.extensions.isValidPassword
import com.example.onlinelearning.utils.prefs.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    usersDao: UsersDao,
    private val mSharedPrefs: SharedPrefs
) : ViewModel() {

    private val mUserRepository = UserRepository(usersDao)

    private val mName = MutableStateFlow("")
    val name = mName.asStateFlow()

    private val mEmail = MutableStateFlow("")
    val email = mEmail.asStateFlow()

    private val mPassword = MutableStateFlow("")
    val password = mPassword.asStateFlow()

    private val mIsPasswordHidden = MutableStateFlow(true)
    val isPasswordHidden = mIsPasswordHidden.asStateFlow()

    private val mUserEntity: UserEntity
        get() = UserEntity(
            username = name.value,
            email = email.value,
            password = password.value
        )

    fun setName(name: String) {
        mName.value = name.trim()
    }

    fun setEmail(email: String) {
        mEmail.value = email.trim()
    }

    fun setPassword(password: String) {
        mPassword.value = password.trim()
    }

    fun setIsPasswordHidden(isHidden: Boolean) {
        mIsPasswordHidden.value = isHidden
    }

    fun validateUserCredentials(onCompletion: (CredentialsValidator?) -> Unit) {
        viewModelScope.launch {
            onCompletion(
                when {
                    isAnyCredentialEmpty() -> CredentialsValidator.EmptyCredentials
                    !isEmailValid() -> CredentialsValidator.InvalidEmailAddress
                    !isPasswordValid() -> CredentialsValidator.InvalidPassword
                    mUserRepository.isUserExists(name.value) -> CredentialsValidator.UserAlreadyExists
                    !mUserRepository.addUser(mUserEntity) -> null
                    else -> {
                        mUserRepository.getUserId(name.value)?.let {
                            setUserLoggedIn(it)
                            CredentialsValidator.Success(it)
                        }
                    }
                }
            )
        }
    }

    private fun setUserLoggedIn(id: Int) {
        mSharedPrefs.isUserLoggedIn = true
        mSharedPrefs.loggedInUserId = id
    }

    private fun isAnyCredentialEmpty(): Boolean =
        name.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()

    private fun isEmailValid(): Boolean =
        email.value.isValidEmailAddress()

    private fun isPasswordValid(): Boolean =
        password.value.isValidPassword()
}