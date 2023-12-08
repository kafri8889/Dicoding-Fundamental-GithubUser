package com.anafthdev.githubuser.ui.repositories

import com.anafthdev.githubuser.data.datasource.remote.ApiClient
import com.anafthdev.githubuser.data.model.ErrorResponse
import com.anafthdev.githubuser.data.model.Repo
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RepositoriesViewModel(
    private val githubRepository: GithubRepository = GithubRepository.getInstance(ApiClient.githubApiService)
): BaseViewModel<RepositoriesState>(RepositoriesState()) {

    fun getRepositories(username: String) {
        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        githubRepository.getRepo(username).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                updateState {
                    copy(
                        isLoading = false,
                        repositories = response.body() ?: emptyList(),
                        errorMsg = response.errorBody().let {
                            if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
                            else ""
                        }
                    )
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
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