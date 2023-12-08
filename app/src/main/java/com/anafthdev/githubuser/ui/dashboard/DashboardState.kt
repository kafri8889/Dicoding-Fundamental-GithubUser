package com.anafthdev.githubuser.ui.dashboard

import com.anafthdev.githubuser.data.model.User

data class DashboardState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val errorMsg: String = ""
)
