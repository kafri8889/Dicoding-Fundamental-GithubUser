package com.anafthdev.githubuser.ui.followers_following

import com.anafthdev.githubuser.data.model.User

data class FollowersFollowingState(
    val users: List<User> = emptyList(),
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
