package com.anafthdev.githubuser.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.anafthdev.githubuser.ProtoUserPreference
import com.anafthdev.githubuser.data.Constant
import com.anafthdev.githubuser.data.datastore.UserPreferenceSerializer
import com.anafthdev.githubuser.data.repository.impl.UserPreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {

    @Provides
    @Singleton
    fun provideUserPreferenceDataStore(
        @ApplicationContext context: Context
    ): DataStore<ProtoUserPreference> = DataStoreFactory.create(
        serializer = UserPreferenceSerializer,
        corruptionHandler = UserPreferenceRepositoryImpl.corruptionHandler,
        produceFile = { context.dataStoreFile(Constant.USER_PREFERENCES_NAME) }
    )

}