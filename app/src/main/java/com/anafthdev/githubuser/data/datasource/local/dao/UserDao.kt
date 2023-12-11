package com.anafthdev.githubuser.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.anafthdev.githubuser.data.model.db.UserDb
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): Flow<List<UserDb>>

    @Update
    suspend fun update(vararg userDb: UserDb)

    @Delete
    suspend fun delete(vararg userDb: UserDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg userDb: UserDb)

}