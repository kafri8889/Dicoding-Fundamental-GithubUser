package com.anafthdev.githubuser.ui.followers_following

import androidx.lifecycle.viewModelScope
import com.anafthdev.githubuser.data.model.response.ErrorResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.anafthdev.githubuser.foundation.extension.toUser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
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

        viewModelScope.launch {
            var errMsg = ""
            val response: Response<List<UserResponse>>

            try {
                response = if (type == FollowersFollowingFragment.TYPE_FOLLOWERS) {
                    githubRepository.getFollowersRemote(username)
                } else githubRepository.getFollowersRemote(username)

                updateState {
                    copy(
                        users = response.body()?.map { it.toUser() } ?: emptyList(),
                    )
                }

                errMsg = response.errorBody().let {
                    if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
                    else ""
                }
            } catch (e: SocketTimeoutException) {
                Timber.e(e, e.message)
                errMsg = e.message ?: ""
            } catch (e: Exception) {
                Timber.e(e, e.message)
                errMsg = e.message ?: ""
            } finally {
                updateState {
                    copy(
                        isLoading = false,
                        errorMsg = errMsg
                    )
                }
            }
        }
    }

}