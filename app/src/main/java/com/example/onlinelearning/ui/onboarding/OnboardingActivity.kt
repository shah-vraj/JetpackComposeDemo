package com.example.onlinelearning.ui.onboarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinelearning.R
import com.example.onlinelearning.data.model.OnboardingData
import com.example.onlinelearning.ui.authentication.AuthenticationActivity
import com.example.onlinelearning.ui.base.BaseComposeActivity
import com.example.onlinelearning.ui.base.TopBar
import com.example.onlinelearning.ui.home.HomeActivity
import com.example.onlinelearning.ui.theme.BaseGreen
import com.example.onlinelearning.ui.theme.LightGrayText
import com.example.onlinelearning.ui.theme.OnlineLearningTheme
import com.example.onlinelearning.ui.theme.ProgressCircleBackground
import com.example.onlinelearning.utils.extensions.startAndFinish
import com.example.onlinelearning.viewmodel.OnboardingViewModel
import com.example.onlinelearning.viewmodel.obtainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class OnboardingActivity : BaseComposeActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineLearningTheme {
                val scope = rememberCoroutineScope()
                val pagerState = rememberPagerState(0)
                val viewModel = obtainViewModel<OnboardingViewModel>()
                val data = viewModel.data
                val currentPage by viewModel.currentPage.collectAsState()
                val currentSweepAngle by viewModel.currentSweepAngle.collectAsState(0f)
                val rightButtonText by viewModel.topBarRightButtonText.collectAsState("")
                val animatedTimerArc by animateFloatAsState(
                    currentSweepAngle,
                    tween(durationMillis = 1000, easing = LinearEasing)
                )

                Scaffold(
                    topBar = {
                        TopBar(
                            onBackButtonClicked = { finish() },
                            onRightButtonClicked = { viewModel.checkAndStartNextActivity() },
                            rightButtonText = rightButtonText
                        )
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(it)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 50.dp)
                        ) {
                            OnboardingPager(
                                data = data,
                                pagerState = pagerState,
                                modifier = Modifier.padding(top = 110.dp)
                            ) { page ->
                                viewModel.onPageChange(page)
                            }
                            ProgressCircle(
                                progressArcSweepAngle = animatedTimerArc,
                                modifier = Modifier.size(80.dp)
                            ) {
                                val isUserOnLastPage = currentPage == data.size - 1
                                if (isUserOnLastPage) {
                                    viewModel.checkAndStartNextActivity()
                                } else {
                                    viewModel.onPageChange(viewModel.currentPage.value + 1)
                                    scope.launch { pagerState.animateScrollToPage(currentPage) }
                                }
                            }
                        }
                    }
                }
            }
        }
        setWhiteStatusBar()
    }

    private fun OnboardingViewModel.checkAndStartNextActivity() {
        setOnboardingCompleted()
        startAndFinish(
            if (isUserLoggedIn)
                HomeActivity::class.java
            else
                AuthenticationActivity::class.java
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(
    data: List<OnboardingData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onPageScrolled: (Int) -> Unit
) {
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { onPageScrolled(it) }
    }

    HorizontalPager(
        count = data.size,
        state = pagerState,
        modifier = modifier
    ) {
        val currentData = data[it]
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(currentData.image),
                contentDescription = "onboardingImage",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 58.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = currentData.title,
                style = MaterialTheme.typography.displayMedium,
                lineHeight = 33.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
            )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = currentData.description,
                style = MaterialTheme.typography.titleSmall,
                lineHeight = 26.sp,
                color = LightGrayText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
            )
        }
    }
}

@Composable
fun ProgressCircle(
    progressArcSweepAngle: Float,
    modifier: Modifier = Modifier,
    onProgressCircleClicked: () -> Unit
) {
    val arcStartAngle = -120f
    val maxSweepAngle = 360f
    val interactionSource = MutableInteractionSource()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onProgressCircleClicked() }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawProgressArc(ProgressCircleBackground, arcStartAngle, maxSweepAngle)
            drawProgressArc(BaseGreen, arcStartAngle, progressArcSweepAngle)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clip(CircleShape)
                .background(BaseGreen)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_forward_arrow),
                contentDescription = "progressCircle"
            )
        }
    }
}

private fun DrawScope.drawProgressArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
    )
}