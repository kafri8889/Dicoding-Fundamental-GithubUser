package com.anafthdev.githubuser.data.repository

import com.anafthdev.githubuser.data.model.db.UserDb
import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GithubRepository {

    suspend fun searchRemote(query: String): Response<SearchResponse>

    suspend fun getUsersRemote(): Response<List<UserResponse>>

    suspend fun getDetailRemote(username: String): Response<UserResponse>

    suspend fun getFollowersRemote(username: String): Response<List<UserResponse>>

    suspend fun getFollowingRemote(username: String): Response<List<UserResponse>>



    fun getAllUserLocal(): Flow<List<UserDb>>

    fun getFavoriteUserLocal(): Flow<List<UserDb>>

    suspend fun updateLocal(vararg userDb: UserDb)

    suspend fun deleteLocal(vararg userDb: UserDb)

    suspend fun insertLocal(vararg userDb: UserDb)

}