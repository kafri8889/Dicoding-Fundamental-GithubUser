package com.anafthdev.githubuser.ui.favorite

import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): BaseViewModel<FavoriteState>(FavoriteState())