package com.example.onlinelearning.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinelearning.R
import com.example.onlinelearning.data.local.entity.UserEntity
import com.example.onlinelearning.data.model.Course
import com.example.onlinelearning.ui.base.BaseLinearProgressIndicator
import com.example.onlinelearning.ui.base.BaseSearchView
import com.example.onlinelearning.ui.theme.BaseGreen
import com.example.onlinelearning.ui.theme.CourseCardGradientEnd
import com.example.onlinelearning.ui.theme.LightGrayText
import com.example.onlinelearning.ui.theme.Shapes
import com.example.onlinelearning.ui.theme.SliderProgressGradientEnd
import com.example.onlinelearning.ui.theme.SliderProgressGradientStart
import com.example.onlinelearning.ui.theme.WhiteO20
import com.example.onlinelearning.ui.theme.WhiteO70
import com.example.onlinelearning.utils.CustomSpannableString
import com.example.onlinelearning.utils.SpannedString
import com.example.onlinelearning.utils.extensions.getText
import com.example.onlinelearning.utils.extensions.shadow
import com.example.onlinelearning.viewmodel.HomeViewModel
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val focusManager = LocalFocusManager.current
    val userData by viewModel.loggedInUserData.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val courses by viewModel.filteredCourses.collectAsState()
    val selectedCourseType by viewModel.selectedCourseType.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { focusManager.clearFocus() }
    ) {
        TitleBlock(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            userData = userData,
            searchText = searchText,
            onSearchTextChange = { viewModel.setSearchText(it) }
        )
        OngoingCourseBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp),
            courses = courses
        )
        ChoiceYourCoursesBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp),
            courses = courses,
            selectedCourseType = selectedCourseType,
            onCourseTypeChanged = { viewModel.setSelectedCourseType(it) }
        )
    }
}

@Composable
private fun TitleBlock(
    modifier: Modifier = Modifier,
    userData: UserEntity?,
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.ic_home_title_background),
            contentDescription = "home_title_background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .clip(Shapes.cornerRadiusBottomTwentyFive)
                .background(BaseGreen)
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 26.dp, horizontal = 25.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(
                            R.string.greeting_title,
                            userData?.username ?: "User"
                        ),
                        style = MaterialTheme.typography.displayLarge,
                        lineHeight = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.lets_start_learning),
                        style = MaterialTheme.typography.headlineLarge,
                        lineHeight = 30.sp,
                        color = Color.White
                    )
                }

                Image(
                    painter = painterResource(R.drawable.ic_profile_pic),
                    contentDescription = "profile_pic"
                )
            }

            BaseSearchView(
                value = searchText,
                onValueChange = onSearchTextChange,
                placeHolder = stringResource(R.string.search_view_placeholder),
                modifier = Modifier
                    .padding(horizontal = 25.dp)
            )
        }
    }
}

@Composable
private fun OngoingCourseBlock(
    modifier: Modifier = Modifier,
    courses: List<Course>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
        ) {
            Text(
                text = stringResource(R.string.ongoing_course),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            CustomSpannableString(
                Modifier,
                SpannedString(
                    text = stringResource(R.string.view_all),
                    textStyle = MaterialTheme.typography.titleSmall,
                    color = BaseGreen
                )
            )
        }
        LazyRow {
            itemsIndexed(courses) { index, course ->
                RectangularCourseItem(
                    background = if (index % 2 == 0)
                        R.drawable.ic_ongoing_course_light_green_background
                    else
                        R.drawable.ic_ongoing_course_dark_green_background,
                    course = course,
                    numberOfLessonsCompleted = when (course.name.getText()) {
                        stringResource(R.string.ui_ux_design) -> 4
                        stringResource(R.string.ux_recharse) -> 7
                        stringResource(R.string.graphic_design) -> 44
                        stringResource(R.string.digital_marketing) -> 69
                        stringResource(R.string.programming) -> 180
                        else -> 0
                    }
                )
            }
        }
    }
}

@Composable
private fun RectangularCourseItem(
    background: Int,
    course: Course,
    numberOfLessonsCompleted: Int
) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(IntrinsicSize.Min)
            .padding(end = 20.dp)
    ) {
        Image(
            painter = painterResource(background),
            contentDescription = "course_background"
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 15.dp, top = 15.dp, bottom = 14.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(Shapes.cornerRadiusTen)
                        .background(Color.White)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google_logo),
                        contentDescription = "course_logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    )
                }
                Text(
                    text = course.name.getText(),
                    style = MaterialTheme.typography.titleMedium,
                    lineHeight = 22.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BaseLinearProgressIndicator(
                    maxProgress = course.numberOfLessons,
                    currentProgress = numberOfLessonsCompleted,
                    containsGradientBackground = true,
                    incompleteProgressBarTint = WhiteO20,
                    progressBarColors = listOf(
                        SliderProgressGradientStart,
                        SliderProgressGradientEnd
                    ),
                    outerThumbTint = SliderProgressGradientEnd
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.number_of_lessons, numberOfLessonsCompleted),
                        style = MaterialTheme.typography.labelMedium,
                        color = WhiteO70,
                        lineHeight = 15.sp
                    )
                    Text(
                        text = stringResource(
                            R.string.number_of_lessons,
                            course.numberOfLessons
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = WhiteO70,
                        lineHeight = 15.sp
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.number_of_videos,
                        course.numberOfLessons
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    lineHeight = 15.sp
                )
                Text(
                    text = stringResource(
                        R.string.number_of_sheets,
                        course.numberOfSheet
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    lineHeight = 15.sp
                )
                Text(
                    text = stringResource(
                        R.string.number_of_quiz,
                        course.numberOfQuiz
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
private fun ChoiceYourCoursesBlock(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    selectedCourseType: CourseType,
    onCourseTypeChanged: (CourseType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp, top = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.choice_your_courses),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            CustomSpannableString(
                Modifier,
                SpannedString(
                    text = stringResource(R.string.view_all),
                    textStyle = MaterialTheme.typography.titleSmall,
                    color = BaseGreen
                )
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(CourseType.values()) {
                CourseTypeItem(
                    courseType = it,
                    isSelected = it == selectedCourseType,
                    onCourseTypeSelected = { onCourseTypeChanged(it) }
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(courses) {
                CourseCard(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 10.dp, bottom = 5.dp),
                    course = it
                )
            }
        }
    }
}

@Composable
fun CourseTypeItem(
    courseType: CourseType,
    isSelected: Boolean,
    onCourseTypeSelected: () -> Unit
) {
    Text(
        text = courseType.type,
        style = if (isSelected)
            MaterialTheme.typography.titleMedium
        else
            MaterialTheme.typography.titleSmall,
        color = if (isSelected) Color.White else LightGrayText,
        modifier = Modifier
            .clip(Shapes.cornerRadiusFive)
            .background(if (isSelected) BaseGreen else Color.Transparent)
            .padding(horizontal = 18.dp, vertical = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onCourseTypeSelected() }
    )
}

@Composable
private fun CourseCard(
    modifier: Modifier = Modifier,
    course: Course
) {
    Card(
        modifier = modifier
            .shadow(
                color = MaterialTheme.colorScheme.surface,
                blurRadius = 30.dp,
                offsetX = 6.dp
            ),
        shape = Shapes.cornerRadiusFifteen
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Box(
                modifier = Modifier.height(95.dp)
            ) {
                Image(
                    painter = painterResource(course.image),
                    contentDescription = "Course Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.cornerRadiusFifteen),
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.cornerRadiusFifteen)
                        .padding(top = 40.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, CourseCardGradientEnd)
                            )
                        )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 9.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = course.name.getText(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    RatingBar(
                        value = course.rating,
                        onValueChange = { },
                        onRatingChanged = { },
                        config = RatingBarConfig()
                            .size(7.5.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.number_of_lessons, course.numberOfLessons),
                        style = MaterialTheme.typography.labelSmall,
                        color = LightGrayText
                    )
                    Text(
                        text = stringResource(R.string.number_of_enrolls, course.numberOfEnrolls),
                        style = MaterialTheme.typography.labelSmall,
                        color = LightGrayText
                    )
                }
            }
        }
    }
}

enum class CourseType(val type: String) {
    ALL("All"),
    POPULAR("Popular")
}