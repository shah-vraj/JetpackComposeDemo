package com.example.onlinelearning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinelearning.data.local.dao.UsersDao
import com.example.onlinelearning.data.local.repository.UserRepository
import com.example.onlinelearning.utils.CredentialsValidator
import com.example.onlinelearning.utils.extensions.isValidEmailAddress
import com.example.onlinelearning.utils.extensions.isValidPassword
import com.example.onlinelearning.utils.prefs.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotAndResetPasswordViewModel(
    private val mUsersDao: UsersDao,
    private val mSharedPrefs: SharedPrefs
) : ViewModel() {

    private val mUserRepository = UserRepository(mUsersDao)
    private var mRequestedUserId: Int? = null

    private val mEmail = MutableStateFlow("")
    val email = mEmail.asStateFlow()

    private val mOtpText1 = MutableStateFlow("")
    val otpText1 = mOtpText1.asStateFlow()

    private val mOtpText2 = MutableStateFlow("")
    val otpText2 = mOtpText2.asStateFlow()

    private val mOtpText3 = MutableStateFlow("")
    val otpText3 = mOtpText3.asStateFlow()

    private val mOtpText4 = MutableStateFlow("")
    val otpText4 = mOtpText4.asStateFlow()

    private val mOtpText5 = MutableStateFlow("")
    val otpText5 = mOtpText5.asStateFlow()

    private val mNewPassword = MutableStateFlow("")
    val newPassword = mNewPassword.asStateFlow()

    private val mConfirmPassword = MutableStateFlow("")
    val confirmPassword = mConfirmPassword.asStateFlow()

    private val mIsNewPasswordHidden = MutableStateFlow(true)
    val isNewPasswordHidden = mIsNewPasswordHidden.asStateFlow()

    private val mIsConfirmPasswordHidden = MutableStateFlow(true)
    val isConfirmPasswordHidden = mIsConfirmPasswordHidden.asStateFlow()

    val isOtpValid: Boolean
        get() = mOtpText1.value.isNotEmpty() &&
                mOtpText2.value.isNotEmpty() &&
                mOtpText3.value.isNotEmpty() &&
                mOtpText4.value.isNotEmpty() &&
                mOtpText5.value.isNotEmpty()

    fun setEmail(email: String) {
        mEmail.value = email.trim()
    }

    fun setOtpText1(text: String) {
        mOtpText1.value = text
    }

    fun setOtpText2(text: String) {
        mOtpText2.value = text
    }

    fun setOtpText3(text: String) {
        mOtpText3.value = text
    }

    fun setOtpText4(text: String) {
        mOtpText4.value = text
    }

    fun setOtpText5(text: String) {
        mOtpText5.value = text
    }

    fun setNewPassword(text: String) {
        mNewPassword.value = text
    }

    fun setConfirmPassword(text: String) {
        mConfirmPassword.value = text
    }

    fun hideNewPassword(isHidden: Boolean) {
        mIsNewPasswordHidden.value = isHidden
    }

    fun hideConfirmPassword(isHidden: Boolean) {
        mIsConfirmPasswordHidden.value = isHidden
    }

    fun validateEmail(completion: (Boolean) -> Unit) {
        viewModelScope.launch {
            completion(
                if (!email.value.isValidEmailAddress())
                    false
                else
                    mUserRepository.getUserWithEmail(email.value)?.let {
                        mRequestedUserId = it.id
                        true
                    } ?: false
            )
        }
    }

    fun updatePassword(completion: (CredentialsValidator) -> Unit) {
        viewModelScope.launch {
            completion(
                when {
                    newPassword.value.isEmpty() || mConfirmPassword.value.isEmpty() ->
                        CredentialsValidator.EmptyCredentials
                    !newPassword.value.isValidPassword() ->
                        CredentialsValidator.InvalidPassword
                    newPassword.value != confirmPassword.value ->
                        CredentialsValidator.WrongPassword
                    else -> {
                        mRequestedUserId?.let {
                            mUserRepository.getUserWithId(it)?.let { userEntity ->
                                val updatedUserEntity = userEntity.copy(password = newPassword.value)
                                mUserRepository.updateUser(updatedUserEntity)
                                CredentialsValidator.Success(it)
                            } ?: CredentialsValidator.UserNotFound
                        } ?: CredentialsValidator.WrongPassword
                    }
                }
            )
        }
    }

    fun setUserLoggedIn() {
        val id = mRequestedUserId ?: return
        mSharedPrefs.loggedInUserId = id
        mSharedPrefs.isUserLoggedIn = true
    }
}