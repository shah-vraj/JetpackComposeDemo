package com.example.onlinelearning.data.model

import android.content.Context
import com.example.onlinelearning.R

data class OnboardingData(
    val image: Int,
    val title: String,
    val description: String
) {
    companion object {
        fun getData(context: Context): List<OnboardingData> = listOf(
            OnboardingData(
                R.drawable.ic_tutorial_teaching,
                context.getString(R.string.onboarding_teaching_title),
                context.getString(R.string.onboarding_common_description)
            ),
            OnboardingData(
                R.drawable.ic_tutorial_learning,
                context.getString(R.string.onboarding_learning_title),
                context.getString(R.string.onboarding_common_description)
            ),
            OnboardingData(
                R.drawable.ic_tutorial_examination,
                context.getString(R.string.onboarding_examination_title),
                context.getString(R.string.onboarding_common_description)
            )
        )
    }
}