package com.example.onlinelearning.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinelearning.data.local.dao.UsersDao
import com.example.onlinelearning.data.local.entity.UserEntity
import com.example.onlinelearning.data.local.repository.UserRepository
import com.example.onlinelearning.data.model.Course
import com.example.onlinelearning.ui.home.CourseType
import com.example.onlinelearning.utils.extensions.getText
import com.example.onlinelearning.utils.prefs.SharedPrefs
import com.example.onlinelearning.utils.prefs.SharedPrefs.Companion.UNKNOWN_USER_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mContext: Application,
    private val mSharedPrefs: SharedPrefs,
    mUsersDao: UsersDao
) : ViewModel() {

    private val mUserRepository = UserRepository(mUsersDao)
    private val courses = Course.getAllCourses()

    private val mLoggedInUserData = MutableStateFlow<UserEntity?>(null)
    val loggedInUserData = mLoggedInUserData.asStateFlow()

    private val mSearchText = MutableStateFlow("")
    val searchText = mSearchText.asStateFlow()

    private val mFilteredCourses = MutableStateFlow(courses)
    val filteredCourses = mFilteredCourses.asStateFlow()

    private val mSelectedCourseType = MutableStateFlow(CourseType.ALL)
    val selectedCourseType = mSelectedCourseType.asStateFlow()

    fun fetchUserDataFor(id: Int) {
        viewModelScope.launch {
            mUserRepository.getUserWithId(
                if (id == UNKNOWN_USER_ID) mSharedPrefs.loggedInUserId else id
            )?.let {
                mLoggedInUserData.value = it
            }
        }
    }

    fun setSearchText(text: String) {
        mSearchText.value = text
        mFilteredCourses.value = courses.filter {
            it.name.getText(mContext).contains(text, true)
        }
    }

    fun setSelectedCourseType(courseType: CourseType) {
        mSelectedCourseType.value = courseType
    }
}