package com.anafthdev.githubuser.data.repository.impl

import com.anafthdev.githubuser.data.datasource.local.dao.UserDao
import com.anafthdev.githubuser.data.datasource.remote.GithubApiService
import com.anafthdev.githubuser.data.model.db.UserDb
import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import com.anafthdev.githubuser.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService,
    private val userDao: UserDao
): GithubRepository {

    override fun searchRemote(query: String): Response<SearchResponse> {
        return githubApiService.search(query)
    }

    override fun getUsersRemote(): Response<List<UserResponse>> {
        return githubApiService.getUsers()
    }

    override fun getDetailRemote(username: String): Response<UserResponse> {
        return githubApiService.getDetail(username)
    }

    override fun getFollowersRemote(username: String): Response<List<UserResponse>> {
        return githubApiService.getFollowers(username)
    }

    override fun getFollowingRemote(username: String): Response<List<UserResponse>> {
        return githubApiService.getFollowing(username)
    }

    override fun getAllUserLocal(): Flow<List<UserDb>> {
        return userDao.getAll()
    }

    override suspend fun updateLocal(vararg userDb: UserDb) {
        userDao.update(*userDb)
    }

    override suspend fun deleteLocal(vararg userDb: UserDb) {
        userDao.delete(*userDb)
    }

    override suspend fun insertLocal(vararg userDb: UserDb) {
        userDao.insert(*userDb)
    }


}