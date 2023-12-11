package com.anafthdev.githubuser.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anafthdev.githubuser.data.datasource.local.dao.UserDao
import com.anafthdev.githubuser.data.model.db.UserDb

@Database(
    entities = [
        UserDb::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
                    .build()
            }.also { INSTANCE = it }
        }
    }
}