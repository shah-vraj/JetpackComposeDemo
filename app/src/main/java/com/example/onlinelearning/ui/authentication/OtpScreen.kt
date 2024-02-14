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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.onlinelearning.R
import com.example.onlinelearning.ui.base.BaseButton
import com.example.onlinelearning.ui.base.TopBar
import com.example.onlinelearning.ui.theme.BaseGreen
import com.example.onlinelearning.ui.theme.BlueText
import com.example.onlinelearning.ui.theme.LightGray
import com.example.onlinelearning.ui.theme.PoppinsFontFamily
import com.example.onlinelearning.ui.theme.Shapes
import com.example.onlinelearning.utils.CustomSpannableString
import com.example.onlinelearning.utils.SpannedString
import com.example.onlinelearning.utils.extensions.ShowShortToast
import com.example.onlinelearning.utils.extensions.moveLeft
import com.example.onlinelearning.utils.extensions.moveRight
import com.example.onlinelearning.utils.extensions.showShortToast
import com.example.onlinelearning.utils.navigation.Authentication
import com.example.onlinelearning.viewmodel.ForgotAndResetPasswordViewModel

@Composable
fun OtpScreen(
    navHostController: NavHostController,
    viewModel: ForgotAndResetPasswordViewModel
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopBar(
                onBackButtonClicked = { navHostController.navigateUp() },
                centerText = stringResource(R.string.verification_code_title)
            )
        }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(95.dp),
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
             * Enter code description
             */
            Text(
                text = stringResource(R.string.enter_the_code_description),
                style = MaterialTheme.typography.titleLarge,
                lineHeight = 28.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            /**
             * OTP block
             */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                OtpView(viewModel = viewModel)

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomSpannableString(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        SpannedString(
                            text = stringResource(R.string.did_not_receive_a_code),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        SpannedString(
                            text = stringResource(R.string.resend_code),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            color = BaseGreen,
                            onClick = {
                                with(context) {
                                    showShortToast(getString(R.string.resend_code_toast_message))
                                }
                            }
                        )
                    )
                }
            }

            /**
             * Confirm Button
             */
            BaseButton(text = stringResource(R.string.confirm)) {
                if (viewModel.isOtpValid) {
                    navHostController.navigate(Authentication.ResetPassword.route)
                } else {
                    ShowShortToast(stringResource(R.string.empty_otp))
                }
            }
        }
    }
}

@Composable
private fun OtpView(
    modifier: Modifier = Modifier,
    viewModel: ForgotAndResetPasswordViewModel
) {
    val focusManager = LocalFocusManager.current
    val otpText1 by viewModel.otpText1.collectAsState()
    val otpText2 by viewModel.otpText2.collectAsState()
    val otpText3 by viewModel.otpText3.collectAsState()
    val otpText4 by viewModel.otpText4.collectAsState()
    val otpText5 by viewModel.otpText5.collectAsState()

    val otpFocusRequester3 = remember { FocusRequester() }
    val otpFocusRequester4 = remember { FocusRequester() }
    val otpFocusRequester5 = remember { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        SingleOtpBlock(
            value = otpText1,
            onValueCleared = {
                viewModel.setOtpText1(it)
                focusManager.clearFocus()
            },
            onValueEntered = {
                viewModel.setOtpText1(it)
                focusManager.moveRight()
            },
            onValueOverflow = {
                viewModel.setOtpText2(it)
                otpFocusRequester3.requestFocus()
            },
            imeAction = ImeAction.Next,
            onNextTapped = { focusManager.moveRight() }
        )
        SingleOtpBlock(
            value = otpText2,
            onValueCleared = {
                viewModel.setOtpText2(it)
                focusManager.moveLeft()
            },
            onValueEntered = {
                viewModel.setOtpText2(it)
                focusManager.moveRight()
            },
            onValueOverflow = {
                viewModel.setOtpText3(it)
                otpFocusRequester4.requestFocus()
            },
            imeAction = ImeAction.Next,
            onNextTapped = { focusManager.moveRight() }
        )
        SingleOtpBlock(
            value = otpText3,
            onValueCleared = {
                viewModel.setOtpText3(it)
                focusManager.moveLeft()
            },
            onValueEntered = {
                viewModel.setOtpText3(it)
                focusManager.moveRight()
            },
            onValueOverflow = {
                viewModel.setOtpText4(it)
                otpFocusRequester5.requestFocus()
            },
            imeAction = ImeAction.Next,
            onNextTapped = { focusManager.moveRight() },
            focusRequester = otpFocusRequester3
        )
        SingleOtpBlock(
            value = otpText4,
            onValueCleared = {
                viewModel.setOtpText4(it)
                focusManager.moveLeft()
            },
            onValueEntered = {
                viewModel.setOtpText4(it)
                focusManager.moveRight()
            },
            onValueOverflow = {
                viewModel.setOtpText5(it)
                focusManager.clearFocus()
            },
            imeAction = ImeAction.Next,
            onNextTapped = { focusManager.moveRight() },
            focusRequester = otpFocusRequester4
        )
        SingleOtpBlock(
            value = otpText5,
            onValueCleared = {
                viewModel.setOtpText5(it)
                focusManager.moveLeft()
            },
            onValueEntered = {
                viewModel.setOtpText5(it)
                focusManager.clearFocus()
            },
            onValueOverflow = { },
            imeAction = ImeAction.Done,
            onDoneTapped = { focusManager.clearFocus() },
            focusRequester = otpFocusRequester5
        )
    }
}

@Composable
fun SingleOtpBlock(
    value: String,
    onValueCleared: (String) -> Unit,
    onValueEntered: (String) -> Unit,
    onValueOverflow: (String) -> Unit,
    imeAction: ImeAction,
    onNextTapped: KeyboardActionScope.() -> Unit = {},
    onDoneTapped: KeyboardActionScope.() -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(52.dp)
            .clip(Shapes.cornerRadiusTen)
            .background(LightGray)
            .padding(top = 5.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                when {
                    it.isEmpty() -> onValueCleared(it)
                    it.length == 1 -> onValueEntered(it)
                    else -> onValueOverflow((it.trim().toInt() % 10).toString())
                }
            },
            textStyle = TextStyle(
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 27.sp,
                lineHeight = 20.sp,
                color = BlueText,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(BaseGreen),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = onNextTapped,
                onDone = onDoneTapped
            ),
            modifier = Modifier.focusRequester(focusRequester)
        )
    }
}