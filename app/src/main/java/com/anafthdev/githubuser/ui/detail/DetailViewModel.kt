package com.anafthdev.githubuser.ui.detail

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

class DetailViewModel(
    private val githubRepository: GithubRepository = GithubRepository.getInstance(ApiClient.githubApiService)
): BaseViewModel<DetailState>(DetailState()) {

    fun getDetail(username: String) {
        if (username == state.value?.user?.login) return

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        githubRepository.getDetail(username).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                updateState {
                    copy(
                        isLoading = false,
                        user = response.body(),
                        errorMsg = response.errorBody().let {
                            if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
                            else ""
                        }
                    )
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
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