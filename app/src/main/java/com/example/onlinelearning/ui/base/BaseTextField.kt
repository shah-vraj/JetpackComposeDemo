package com.example.onlinelearning.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinelearning.ui.theme.BaseGreen
import com.example.onlinelearning.ui.theme.LightGray
import com.example.onlinelearning.ui.theme.PoppinsFontFamily
import com.example.onlinelearning.ui.theme.Shapes

@Composable
@Preview
fun BaseTextField(
    modifier: Modifier = Modifier,
    textFieldValue: String = "",
    onValueChanged: (String) -> Unit = { },
    placeholder: String = "",
    hideText: Boolean = false,
    rightButtonIcon: Int? = null,
    onRightButtonClicked: () -> Unit = { },
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    focusRequester: FocusRequester = FocusRequester()
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
            .clip(Shapes.cornerRadiusTen)
            .background(LightGray)
            .padding(start = 19.dp, end = 21.dp)
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { onValueChanged(it) },
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal
            ),
            singleLine = true,
            visualTransformation = if (hideText)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->
                Box {
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                    }
                }
                innerTextField()
            },
            cursorBrush = SolidColor(BaseGreen),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        rightButtonIcon?.let {
            Image(
                painter = painterResource(it),
                contentDescription = "",
                modifier = Modifier
                    .width(12.dp)
                    .height(10.dp)
                    .align(Alignment.CenterEnd)
                    .focusRequester(focusRequester)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onRightButtonClicked() }
            )
        }
    }
}