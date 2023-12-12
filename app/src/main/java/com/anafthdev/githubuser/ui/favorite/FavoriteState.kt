package com.anafthdev.githubuser.ui.favorite

import com.anafthdev.githubuser.data.model.User

data class FavoriteState(
    val users: List<User> = emptyList(),
)
