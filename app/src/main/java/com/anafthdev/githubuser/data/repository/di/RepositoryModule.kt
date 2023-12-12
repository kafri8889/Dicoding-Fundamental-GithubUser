package com.anafthdev.githubuser.data.repository.di

import androidx.datastore.core.DataStore
import com.anafthdev.githubuser.ProtoUserPreference
import com.anafthdev.githubuser.data.datasource.local.dao.UserDao
import com.anafthdev.githubuser.data.datasource.remote.GithubApiService
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.data.repository.UserPreferenceRepository
import com.anafthdev.githubuser.data.repository.impl.GithubRepositoryImpl
import com.anafthdev.githubuser.data.repository.impl.UserPreferenceRepositoryImpl
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

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(
        dataStore: DataStore<ProtoUserPreference>
    ): UserPreferenceRepository = UserPreferenceRepositoryImpl(dataStore)

}