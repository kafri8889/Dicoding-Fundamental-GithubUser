package com.anafthdev.githubuser.ui.favorite

import androidx.lifecycle.viewModelScope
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.anafthdev.githubuser.foundation.extension.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): BaseViewModel<FavoriteState>(FavoriteState()) {

    init {
        viewModelScope.launch {
            githubRepository.getFavoriteUserLocal().collectLatest { users ->
                updateState {
                    copy(
                        users = users.map { it.toUser() }
                    )
                }
            }
        }
    }

}
