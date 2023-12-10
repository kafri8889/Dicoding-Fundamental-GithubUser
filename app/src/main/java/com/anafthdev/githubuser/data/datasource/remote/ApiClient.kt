package com.anafthdev.githubuser.data.datasource.remote

import com.anafthdev.githubuser.BuildConfig
import com.anafthdev.githubuser.data.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
    }

    private val authInterceptor: Interceptor by lazy {
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "token ${Constant.GITHUB_TOKEN}")
                    .build()
            )
        }
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.GITHUB_API_ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val githubApiService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

}