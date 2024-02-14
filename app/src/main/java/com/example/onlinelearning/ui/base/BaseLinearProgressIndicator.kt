package com.example.onlinelearning.ui.base

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.onlinelearning.ui.theme.BlackO10
import com.example.onlinelearning.utils.extensions.toDp
import kotlinx.coroutines.launch

@Composable
fun BaseLinearProgressIndicator(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    currentProgress: Int = 100,
    maxProgress: Int = 100,
    completedProgressBarTint: Color = Color.Black,
    incompleteProgressBarTint: Color = Color.LightGray,
    outerThumbTint: Color = Color.Black,
    innerThumbTint: Color = Color.White,
    containsGradientBackground: Boolean = false,
    progressBarColors: List<Color> = listOf(Color.White, Color.Black)
) {
    var canvasSize by remember { mutableStateOf(Size(0f, 0f)) }
    val thumbOffsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(cornerRadius))
        ) {
            canvasSize = size
            drawRoundRect(
                color = incompleteProgressBarTint,
                size = size
            )

            if (containsGradientBackground)
                drawRoundRect(
                    brush = Brush.linearGradient(colors = progressBarColors),
                    size = Size(
                        (currentProgress * size.width) / maxProgress,
                        size.height
                    ).also {
                        scope.launch { thumbOffsetX.snapTo(it.width - it.height) }
                    },
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            else
                drawRoundRect(
                    color = completedProgressBarTint,
                    size = Size(
                        (currentProgress * size.width) / maxProgress,
                        size.height
                    ).also {
                        scope.launch { thumbOffsetX.snapTo(it.width - it.height) }
                    },
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(thumbOffsetX.value.toInt(), 0) }
                .shadow(
                    elevation = 5.dp,
                    shape = CircleShape,
                    ambientColor = BlackO10
                )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size((canvasSize.height.times(2)).toDp())
                    .clip(CircleShape)
                    .background(outerThumbTint)
            ) {
                Box(
                    modifier = Modifier
                        .size(canvasSize.height.toDp())
                        .clip(CircleShape)
                        .background(innerThumbTint)
                )
            }
        }
    }
}