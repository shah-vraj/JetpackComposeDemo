package com.example.onlinelearning.ui.base

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.onlinelearning.R
import com.example.onlinelearning.ui.theme.Shapes
import com.example.onlinelearning.utils.navigation.BottomNavigationItem
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    var selectedTabIndicatorWidth by remember { mutableStateOf(0) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val bottomNavigationItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Search,
        BottomNavigationItem.MyCourses,
        BottomNavigationItem.Message,
        BottomNavigationItem.Profile
    )
    val bottomNavigationItemXOffsetMap = buildMap<BottomNavigationItem, Float?> {
        bottomNavigationItems.forEach { put(it, null) }
    }.toMutableMap()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(Shapes.cornerRadiusTopThirty)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            bottomNavigationItems.forEach {
                val isSelected = it.route == backStackEntry.value?.destination?.route
                BottomNavigationIcon(
                    bottomNavigationItem = it,
                    isSelected = isSelected,
                    onClick = {
                        scope.launch {
                            offsetX.animateTo(
                                targetValue = bottomNavigationItemXOffsetMap[it] ?: 0f,
                                tween(
                                    durationMillis = 300,
                                    easing = EaseOut
                                )
                            )
                            navHostController.navigate(it.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            val width = coordinates.size.width.toFloat()
                            val currentXOffset = coordinates.positionInRoot().x
                            val xOffsetThreshold = (width - selectedTabIndicatorWidth) / 2
                            bottomNavigationItemXOffsetMap[it] = currentXOffset + xOffsetThreshold
                            if (isSelected)
                                scope.launch {
                                    bottomNavigationItemXOffsetMap[it]?.let { offset ->
                                        offsetX.snapTo(offset)
                                    }
                                }
                        }
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.ic_selected_tab_indicator),
            contentDescription = "selected_tab_indicator",
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .align(Alignment.BottomStart)
                .onGloballyPositioned { selectedTabIndicatorWidth = it.size.width }
        )
    }
}

@Composable
fun BottomNavigationIcon(
    bottomNavigationItem: BottomNavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 25.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onClick() }
    ) {
        Image(
            painter = painterResource(bottomNavigationItem.getImageFor(isSelected)),
            contentDescription = bottomNavigationItem.route
        )
    }
}

private fun BottomNavigationItem.getImageFor(isSelected: Boolean): Int = when (this) {
    BottomNavigationItem.Home -> {
        if (isSelected)
            R.drawable.ic_home_selected
        else
            R.drawable.ic_home_unselected
    }
    BottomNavigationItem.Search -> {
        if (isSelected)
            R.drawable.ic_search_selected
        else
            R.drawable.ic_search_unselected
    }
    BottomNavigationItem.MyCourses -> {
        if (isSelected)
            R.drawable.ic_my_courses_selected
        else
            R.drawable.ic_my_courses_unselected
    }
    BottomNavigationItem.Message -> {
        if (isSelected)
            R.drawable.ic_message_selected
        else
            R.drawable.ic_message_unselected
    }
    BottomNavigationItem.Profile -> {
        if (isSelected)
            R.drawable.ic_profile_selected
        else
            R.drawable.ic_profile_unselected
    }
}