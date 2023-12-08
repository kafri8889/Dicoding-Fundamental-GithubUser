package com.anafthdev.githubuser

import android.app.Application
import timber.log.Timber

class GithubUserApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}