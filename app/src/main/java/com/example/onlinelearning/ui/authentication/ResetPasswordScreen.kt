package com.example.onlinelearning.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.onlinelearning.R
import com.example.onlinelearning.ui.base.BaseButton
import com.example.onlinelearning.ui.base.BaseTextField
import com.example.onlinelearning.ui.base.TopBar
import com.example.onlinelearning.utils.CredentialsValidator
import com.example.onlinelearning.utils.extensions.showShortToast
import com.example.onlinelearning.viewmodel.ForgotAndResetPasswordViewModel

@Composable
fun ResetPasswordScreen(
    navHostController: NavHostController,
    viewModel: ForgotAndResetPasswordViewModel,
    onStartHomeActivity: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val newPassword by viewModel.newPassword.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isNewPasswordHidden by viewModel.isNewPasswordHidden.collectAsState()
    val isConfirmPasswordHidden by viewModel.isConfirmPasswordHidden.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                onBackButtonClicked = { navHostController.navigateUp() },
                centerText = stringResource(R.string.reset_password_title)
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(padding)
                .padding(top = 20.dp)
                .padding(horizontal = 25.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { focusManager.clearFocus() }
        ) {
            /**
             * Enter a new password title
             */
            Text(
                text = stringResource(R.string.enter_new_password),
                style = MaterialTheme.typography.titleLarge,
                lineHeight = 28.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(96.dp))

            /**
             * New password text fields
             */
            BaseTextField(
                textFieldValue = newPassword,
                onValueChanged = { viewModel.setNewPassword(it) },
                placeholder = stringResource(R.string.new_password_placeholder),
                hideText = isNewPasswordHidden,
                rightButtonIcon = R.drawable.ic_password_eye,
                onRightButtonClicked = { viewModel.hideNewPassword(!isNewPasswordHidden) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            BaseTextField(
                textFieldValue = confirmPassword,
                onValueChanged = { viewModel.setConfirmPassword(it) },
                placeholder = stringResource(R.string.confirm_password_placeholder),
                hideText = isConfirmPasswordHidden,
                rightButtonIcon = R.drawable.ic_password_eye,
                onRightButtonClicked = { viewModel.hideConfirmPassword(!isConfirmPasswordHidden) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            
            Spacer(modifier = Modifier.height(55.dp))
            
            BaseButton(text = stringResource(R.string.confirm)) {
                viewModel.updatePassword {
                    with(context) {
                        when (it) {
                            CredentialsValidator.EmptyCredentials ->
                                showShortToast(getString(R.string.empty_credentials))
                            CredentialsValidator.InvalidPassword ->
                                showShortToast(getString(R.string.invalid_password))
                            CredentialsValidator.UserNotFound ->
                                showShortToast(getString(R.string.user_doesnt_exists))
                            CredentialsValidator.WrongPassword ->
                                showShortToast(getString(R.string.password_not_matching))
                            is CredentialsValidator.Success -> {
                                viewModel.setUserLoggedIn()
                                onStartHomeActivity(it.id)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}