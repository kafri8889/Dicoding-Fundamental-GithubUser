package com.anafthdev.githubuser.data.repository

import com.anafthdev.githubuser.data.model.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    val getUserPreference: Flow<UserPreference>

    suspend fun setIsDarkTheme(value: Boolean)

}