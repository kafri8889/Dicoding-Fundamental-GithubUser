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

    @Query("select * from user where isFavorite_user = 1")
    fun getFavoriteUser(): Flow<List<UserDb>>

    @Query("select * from user where login_user like :username")
    fun getUserByUsername(username: String): Flow<UserDb?>

    @Update
    suspend fun update(vararg userDb: UserDb)

    @Delete
    suspend fun delete(vararg userDb: UserDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg userDb: UserDb)

}