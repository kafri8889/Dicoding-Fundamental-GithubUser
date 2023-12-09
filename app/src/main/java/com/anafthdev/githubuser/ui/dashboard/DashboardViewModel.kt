package com.anafthdev.githubuser.ui.dashboard

import com.anafthdev.githubuser.data.datasource.remote.ApiClient
import com.anafthdev.githubuser.data.model.ErrorResponse
import com.anafthdev.githubuser.data.model.SearchResponse
import com.anafthdev.githubuser.data.model.User
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DashboardViewModel(
    private val githubRepository: GithubRepository = GithubRepository.getInstance(ApiClient.githubApiService)
): BaseViewModel<DashboardState>(DashboardState()) {

    init {
        loadUsers()
    }

    private fun loadUsers() {
        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        githubRepository.getUsers().enqueue(object : Callback<List<User>> {
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

    fun search(query: String) {
        // Jika query kosong, load random user
        if (query.isBlank()) {
            loadUsers()
            return
        }

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        githubRepository.search(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                updateState {
                    copy(
                        isLoading = false,
                        users = response.body()?.users ?: emptyList(),
                        errorMsg = response.errorBody().let {
                            if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
                            else ""
                        }
                    )
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
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