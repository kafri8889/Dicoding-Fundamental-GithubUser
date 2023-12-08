package com.anafthdev.githubuser.data.repository

import com.anafthdev.githubuser.data.datasource.remote.GithubApiService
import com.anafthdev.githubuser.data.model.SearchResponse
import com.anafthdev.githubuser.data.model.User
import retrofit2.Call

class GithubRepository(
    private val githubApiService: GithubApiService
) {

    fun search(query: String): Call<SearchResponse> {
        return githubApiService.search(query)
    }

    fun getUsers(): Call<List<User>> {
        return githubApiService.getUsers()
    }

    fun getDetail(username: String): Call<User> {
        return githubApiService.getDetail(username)
    }

    fun getFollowers(username: String): Call<List<User>> {
        return githubApiService.getFollowers(username)
    }

    fun getFollowing(username: String): Call<List<User>> {
        return githubApiService.getFollowing(username)
    }

    // Repository tanpa dependency injection
    companion object {
        private var INSTANCE: GithubRepository? = null

        fun getInstance(githubApiService: GithubApiService): GithubRepository {
            return INSTANCE ?: synchronized(GithubRepository::class) {
                GithubRepository(githubApiService)
            }.also { INSTANCE = it }
        }
    }
}