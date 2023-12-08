package com.anafthdev.githubuser.ui.detail

import com.anafthdev.githubuser.data.model.User

data class DetailState(
    val user: User? = null,
    val errorMsg: String = "",
    val isLoading: Boolean = false
)
