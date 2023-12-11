package com.anafthdev.githubuser.data.datasource.local.di

import android.content.Context
import com.anafthdev.githubuser.data.datasource.local.AppDatabase
import com.anafthdev.githubuser.data.datasource.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDao(
        appDatabase: AppDatabase
    ): UserDao = appDatabase.userDao()

}