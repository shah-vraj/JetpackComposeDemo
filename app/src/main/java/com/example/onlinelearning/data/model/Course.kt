package com.example.onlinelearning.data.model

import com.example.onlinelearning.R
import com.example.onlinelearning.utils.Text

data class Course(
    val name: Text,
    val image: Int,
    val numberOfLessons: Int,
    val numberOfSheet: Int,
    val numberOfQuiz: Int,
    val rating: Float,
    val numberOfEnrolls: Int
) {
    companion object {
        fun getAllCourses(): List<Course> = listOf(
            Course(
                name = Text.StringResource(R.string.ui_ux_design),
                image = R.drawable.ic_ui_ux_design,
                numberOfLessons = 24,
                numberOfSheet = 15,
                numberOfQuiz = 20,
                rating = 4f,
                numberOfEnrolls = 110
            ),
            Course(
                name = Text.StringResource(R.string.ux_recharse),
                image = R.drawable.ic_ux_recharse,
                numberOfLessons = 10,
                numberOfSheet = 5,
                numberOfQuiz = 8,
                rating = 3.5f,
                numberOfEnrolls = 55
            ),
            Course(
                name = Text.StringResource(R.string.graphic_design),
                image = R.drawable.ic_graphic_design,
                numberOfLessons = 54,
                numberOfSheet = 30,
                numberOfQuiz = 25,
                rating = 4.5f,
                numberOfEnrolls = 1104
            ),
            Course(
                name = Text.StringResource(R.string.digital_marketing),
                image = R.drawable.ic_digital_marketing,
                numberOfLessons = 120,
                numberOfSheet = 66,
                numberOfQuiz = 90,
                rating = 3.85f,
                numberOfEnrolls = 2357
            ),
            Course(
                name = Text.StringResource(R.string.programming),
                image = R.drawable.ic_programming,
                numberOfLessons = 180,
                numberOfSheet = 90,
                numberOfQuiz = 120,
                rating = 5f,
                numberOfEnrolls = 67546
            )
        )
    }
}
