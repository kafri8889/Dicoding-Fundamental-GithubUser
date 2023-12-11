package com.anafthdev.githubuser.data.datasource.remote

import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    fun search(
        @Query("q") username: String
    ): Response<SearchResponse>

    @GET("users")
    fun getUsers(): Response<List<UserResponse>>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Response<UserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Response<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Response<List<UserResponse>>

}