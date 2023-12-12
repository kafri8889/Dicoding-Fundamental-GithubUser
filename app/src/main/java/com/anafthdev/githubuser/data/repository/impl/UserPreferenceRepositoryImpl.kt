package com.anafthdev.githubuser.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.anafthdev.githubuser.ProtoUserPreference
import com.anafthdev.githubuser.data.model.UserPreference
import com.anafthdev.githubuser.data.repository.UserPreferenceRepository
import com.anafthdev.githubuser.foundation.extension.toUserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val userPreferenceDatastore: DataStore<ProtoUserPreference>
): UserPreferenceRepository {

    override val getUserPreference: Flow<UserPreference>
        get() = userPreferenceDatastore.data.map { it.toUserPreference() }

    override suspend fun setIsDarkTheme(value: Boolean) {
        userPreferenceDatastore.updateData {
            it.copy(
                isDarkTheme = value
            )
        }
    }

    override suspend fun setIsDynamicColor(value: Boolean) {
        userPreferenceDatastore.updateData {
            it.copy(
                isDynamicColor = value
            )
        }
    }

    companion object {
        val corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { ProtoUserPreference() }
        )
    }
}