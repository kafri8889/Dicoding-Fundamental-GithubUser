package com.anafthdev.githubuser.ui.followers_following

import com.anafthdev.githubuser.data.datasource.remote.ApiClient
import com.anafthdev.githubuser.data.model.ErrorResponse
import com.anafthdev.githubuser.data.model.User
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FollowersFollowingViewModel(
    private val githubRepository: GithubRepository = GithubRepository.getInstance(ApiClient.githubApiService)
): BaseViewModel<FollowersFollowingState>(FollowersFollowingState()) {

    /**
     * @param type [FollowersFollowingFragment.EXTRA_TYPE]
     */
    fun getFollowersOrFollowing(username: String?, type: String?) {
        if (username == null || type == null) return

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        val request = if (type == FollowersFollowingFragment.TYPE_FOLLOWERS) {
            githubRepository.getFollowers(username)
        } else githubRepository.getFollowing(username)

        request.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                updateState {
                    copy(
                        isLoading = false,
                        users = response.body() ?: emptyList(),
                        errorMsg = response.errorBody().let {
                            if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
                            else ""
                        }
                    )
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Timber.e(t, t.message)

                updateState {
                    copy(
                        isLoading = false,
                        errorMsg = t.message ?: ""
                    )
                }
            }
        })
    }

}