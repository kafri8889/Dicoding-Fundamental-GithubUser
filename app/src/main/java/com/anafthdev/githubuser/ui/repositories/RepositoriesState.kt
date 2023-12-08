package com.anafthdev.githubuser.ui.repositories

import com.anafthdev.githubuser.data.model.Repo

data class RepositoriesState(
    val errorMsg: String = "",
    val isLoading: Boolean = false,
    val repositories: List<Repo> = emptyList()
)
