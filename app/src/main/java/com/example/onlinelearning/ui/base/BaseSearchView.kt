package com.example.onlinelearning.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.onlinelearning.R
import com.example.onlinelearning.ui.theme.BaseGreen
import com.example.onlinelearning.ui.theme.BlueText
import com.example.onlinelearning.ui.theme.LightGrayText

@Composable
@Preview
fun BaseSearchView(
    modifier: Modifier = Modifier,
    leadingIcon: Int = R.drawable.ic_search_alternate,
    placeHolder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    filterIcon: Int = R.drawable.ic_filter,
    backgroundColor: Color = Color.White,
    cornerRadius: Dp = 10.dp
) {
    val focusManager = LocalFocusManager.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 9.dp)
                .weight(1f)
        ) {
            Image(painter = painterResource(leadingIcon), contentDescription = "")

            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                textStyle = MaterialTheme.typography.titleSmall.copy(
                    color = BlueText
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeHolder,
                                style = MaterialTheme.typography.titleSmall,
                                color = LightGrayText,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            )
                        }
                    }
                    innerTextField()
                },
                cursorBrush = SolidColor(BaseGreen)
            )
        }

        Image(painter = painterResource(filterIcon), contentDescription = "")
    }
}