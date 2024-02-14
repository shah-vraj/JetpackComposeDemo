package com.example.onlinelearning.utils.navigation

sealed class BottomNavigationItem(val route: String) {
    object Home : BottomNavigationItem("home")
    object Search : BottomNavigationItem("search")
    object MyCourses : BottomNavigationItem("my_courses")
    object Message : BottomNavigationItem("message")
    object Profile : BottomNavigationItem("profile")
}