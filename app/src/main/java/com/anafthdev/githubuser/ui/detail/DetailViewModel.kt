package com.anafthdev.githubuser.ui.detail

import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): BaseViewModel<DetailState>(DetailState()) {

    fun getDetail(username: String) {
        if (username == state.value?.user?.login) return

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }


    }

}