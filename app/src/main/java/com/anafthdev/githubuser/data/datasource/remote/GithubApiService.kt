package com.anafthdev.githubuser.data.datasource.remote

import com.anafthdev.githubuser.data.Constant
import com.anafthdev.githubuser.data.model.Repo
import com.anafthdev.githubuser.data.model.SearchResponse
import com.anafthdev.githubuser.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    fun search(
        @Query("q") username: String,
        @Header("Authorization") token: String = Constant.GITHUB_TOKEN
    ): Call<SearchResponse>

    @GET("users")
    fun getUsers(
        @Header("Authorization") token: String = Constant.GITHUB_TOKEN
    ): Call<List<User>>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String,
        @Header("Authorization") token: String = Constant.GITHUB_TOKEN
    ): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String = Constant.GITHUB_TOKEN
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String = Constant.GITHUB_TOKEN
    ): Call<List<User>>

    @GET("users/{username}/repos")
    fun getRepo(
        @Path("username") username: String,
        @Header("Authorization") token: String = Constant.GITHUB_TOKEN
    ): Call<List<Repo>>

}