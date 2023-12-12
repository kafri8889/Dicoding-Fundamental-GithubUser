package com.anafthdev.githubuser.data.datasource.remote

import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    suspend fun search(
        @Query("q") username: String
    ): Response<SearchResponse>

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("users/{username}")
    suspend fun getDetail(
        @Path("username") username: String
    ): Response<UserResponse>

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<List<UserResponse>>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): Response<List<UserResponse>>

}