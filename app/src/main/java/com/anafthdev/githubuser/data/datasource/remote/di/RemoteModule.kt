package com.anafthdev.githubuser.data.datasource.remote.di

import com.anafthdev.githubuser.BuildConfig
import com.anafthdev.githubuser.data.datasource.remote.ApiClient
import com.anafthdev.githubuser.data.datasource.remote.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(ApiClient.client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGithubApiService(
        retrofit: Retrofit
    ): GithubApiService = retrofit.create(GithubApiService::class.java)

}