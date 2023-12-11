package com.anafthdev.githubuser.ui.followers_following

import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowersFollowingViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): BaseViewModel<FollowersFollowingState>(FollowersFollowingState()) {

    /**
     * @param type [FollowersFollowingFragment.EXTRA_TYPE]
     */
    fun getFollowersOrFollowing(username: String?, type: String?) {
        if (username == null || type == null) return

        updateState {
            copy(
                username = username,
                type = type,
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

//        val request = if (type == FollowersFollowingFragment.TYPE_FOLLOWERS) {
//            githubRepository.getFollowingLocal(username)
//        } else githubRepository.getFollowingLocal(username)
    }

}