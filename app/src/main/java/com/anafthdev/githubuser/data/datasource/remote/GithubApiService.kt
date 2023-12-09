package com.anafthdev.githubuser.data.datasource.remote

import com.anafthdev.githubuser.data.model.SearchResponse
import com.anafthdev.githubuser.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    fun search(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>

}