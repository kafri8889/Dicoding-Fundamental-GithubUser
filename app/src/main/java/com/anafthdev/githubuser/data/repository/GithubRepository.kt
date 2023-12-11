package com.anafthdev.githubuser.data.repository

import com.anafthdev.githubuser.data.model.db.UserDb
import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GithubRepository {

    fun searchRemote(query: String): Response<SearchResponse>

    fun getUsersRemote(): Response<List<UserResponse>>

    fun getDetailRemote(username: String): Response<UserResponse>

    fun getFollowersRemote(username: String): Response<List<UserResponse>>

    fun getFollowingRemote(username: String): Response<List<UserResponse>>



    fun getAllUserLocal(): Flow<List<UserDb>>

    suspend fun updateLocal(vararg userDb: UserDb)

    suspend fun deleteLocal(vararg userDb: UserDb)

    suspend fun insertLocal(vararg userDb: UserDb)

}