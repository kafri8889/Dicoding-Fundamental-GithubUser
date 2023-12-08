package com.anafthdev.githubuser.data.datasource.remote

import com.anafthdev.githubuser.BuildConfig
import com.anafthdev.githubuser.data.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.GITHUB_API_ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val githubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

}