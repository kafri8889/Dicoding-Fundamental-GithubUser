package com.anafthdev.githubuser.ui.followers_following

import com.anafthdev.githubuser.data.model.User

data class FollowersFollowingState(
    val type: String = "",
    val username: String = "",
    val users: List<User> = emptyList(),
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
