package com.example.onlinelearning.utils

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle

data class SpannedString(
    val text: String,
    val color: Color = Color.Black,
    val textStyle: TextStyle = TextStyle(),
    val fontStyle: FontStyle = FontStyle.Normal,
    val onClick: () -> Unit = { }
)

@Composable
fun CustomSpannableString(
    modifier: Modifier = Modifier,
    vararg spannedStrings: SpannedString
) {
    val annotatedString = buildAnnotatedString {
        spannedStrings.forEach {
            with(it) {
                pushStringAnnotation(text, "")
                withStyle(
                    SpanStyle(
                        fontFamily = textStyle.fontFamily,
                        fontWeight = textStyle.fontWeight,
                        fontSize = textStyle.fontSize,
                        color = color,
                        fontStyle = fontStyle
                    )
                ) { append(text) }
                pop()
            }
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = modifier
    ) {
        spannedStrings.forEachIndexed { index, string ->
            annotatedString
                .getStringAnnotations(string.text, it, it)
                .firstOrNull()
                ?.let {
                    spannedStrings[index].onClick.invoke()
                }
        }
    }
}