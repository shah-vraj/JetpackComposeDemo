package com.example.onlinelearning.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.onlinelearning.R
import com.example.onlinelearning.ui.base.BaseButton
import com.example.onlinelearning.ui.base.BaseLeftImageButton
import com.example.onlinelearning.ui.base.BaseTextField
import com.example.onlinelearning.ui.base.TopBar
import com.example.onlinelearning.ui.theme.BaseGreen
import com.example.onlinelearning.utils.CredentialsValidator
import com.example.onlinelearning.utils.CustomSpannableString
import com.example.onlinelearning.utils.SpannedString
import com.example.onlinelearning.utils.extensions.navigateWithNoBackStack
import com.example.onlinelearning.utils.extensions.showShortToast
import com.example.onlinelearning.utils.navigation.Authentication
import com.example.onlinelearning.viewmodel.SignInViewModel
import com.example.onlinelearning.viewmodel.obtainViewModel

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    onSignInSuccess: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val viewModel = obtainViewModel<SignInViewModel>()
    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordHidden by viewModel.isPasswordHidden.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                onBackButtonClicked = {
                    navHostController.navigateWithNoBackStack(Authentication.SignInSignUp.route)
                },
                centerText = stringResource(R.string.sign_in_title)
            )
        }
    ) { padding ->
        /**
         * Parent view
         */
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(padding)
                .padding(horizontal = 25.dp)
                .verticalScroll(scrollState)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { focusManager.clearFocus() }
        ) {
            /**
             * Welcome back title
             */
            Text(
                text = stringResource(R.string.welcome_back_title),
                style = MaterialTheme.typography.displayLarge,
                lineHeight = 39.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            /**
             * TextFields and forgot password button block
             */
            Column(
                horizontalAlignment = Alignment.End
            ) {
                BaseTextField(
                    textFieldValue = name,
                    onValueChanged = { viewModel.setName(it) },
                    placeholder = stringResource(R.string.placeholder_name),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                BaseTextField(
                    textFieldValue = password,
                    onValueChanged = { viewModel.setPassword(it) },
                    hideText = isPasswordHidden,
                    placeholder = stringResource(R.string.placeholder_password),
                    rightButtonIcon = R.drawable.ic_password_eye,
                    onRightButtonClicked = { viewModel.setIsPasswordHidden(!isPasswordHidden) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Right,
                    color = BaseGreen,
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            navHostController.navigate(Authentication.ForgotPassword.route)
                        }
                )
            }

            /**
             * Sign in and navigate to sign up button block
             */
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 40.dp)
            ) {
                BaseButton(
                    text = stringResource(id = R.string.sign_in)
                ) {
                    viewModel.validateUserCredentials {
                        context.apply {
                            when (it) {
                                CredentialsValidator.EmptyCredentials ->
                                    showShortToast(getString(R.string.empty_credentials))
                                CredentialsValidator.UserNotFound ->
                                    showShortToast(getString(R.string.user_doesnt_exists, name))
                                CredentialsValidator.WrongPassword ->
                                    showShortToast(getString(R.string.wrong_password))
                                is CredentialsValidator.Success -> onSignInSuccess(it.id)
                                else -> {}
                            }
                        }
                    }
                }

                CustomSpannableString(
                    spannedStrings = arrayOf(
                        SpannedString(
                            text = stringResource(R.string.dont_have_an_account),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        SpannedString(
                            text = stringResource(R.string.sign_up),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            color = BaseGreen,
                            onClick = {
                                navHostController.navigate(Authentication.SignUp.route) {
                                    popUpTo(Authentication.SignInSignUp.route) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    )
                )
            }

            /**
             * Google or apple login option block
             */
            GoogleOrAppleLoginBlock()
        }
    }
}

@Composable
fun GoogleOrAppleLoginBlock() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .weight(1f)
            )
            Text(
                text = stringResource(R.string.or_continue_with),
                style = MaterialTheme.typography.titleSmall,
                lineHeight = 15.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(19.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            BaseLeftImageButton(
                text = stringResource(R.string.google),
                image = R.drawable.ic_google_logo,
                modifier = Modifier.weight(1f)
            )
            BaseLeftImageButton(
                text = stringResource(R.string.apple),
                image = R.drawable.ic_apple_logo,
                modifier = Modifier.weight(1f)
            )
        }
    }
}