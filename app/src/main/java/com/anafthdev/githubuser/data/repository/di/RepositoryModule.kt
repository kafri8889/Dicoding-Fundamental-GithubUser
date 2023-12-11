package com.anafthdev.githubuser.data.repository.di

import com.anafthdev.githubuser.data.datasource.local.dao.UserDao
import com.anafthdev.githubuser.data.datasource.remote.GithubApiService
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.data.repository.impl.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepository(
        githubApiService: GithubApiService,
        userDao: UserDao
    ): GithubRepository = GithubRepositoryImpl(githubApiService, userDao)

}