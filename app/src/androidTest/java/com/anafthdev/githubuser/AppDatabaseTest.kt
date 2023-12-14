package com.anafthdev.githubuser

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.anafthdev.githubuser.data.datasource.local.AppDatabase
import com.anafthdev.githubuser.data.datasource.local.dao.UserDao
import com.anafthdev.githubuser.data.model.db.UserDb
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    private val users = arrayOf(
        UserDb(
            id = Random.nextInt(),
            login = "kafri8889",
            followers = 200,
            following = 100
        ),
        UserDb(
            id = Random.nextInt(),
            login = "kafri888",
            followers = 4341,
            following = 513,
            isFavorite = true
        )
    )

    @Before
    fun create_db() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun close_db() {
        db.close()
    }

    @Test
    fun crudTest() {
        runBlocking {
            userDao.insert(*users)
            assert(userDao.getAll().firstOrNull()?.size == 2) {
                "Insert failed"
            }

            assert(userDao.getUserByUsername(users[0].login).firstOrNull()?.followers == users[0].followers) {
                "Get by username failed"
            }

            assert(userDao.getFavoriteUser().firstOrNull()?.get(0)?.login == users[1].login) {
                "Get favorite user failed"
            }

            userDao.update(users[0].copy(followers = 2000))
            assert(userDao.getUserByUsername(users[0].login).firstOrNull()?.followers == 2000) {
                "Update failed"
            }

            userDao.delete(*users)
            assert(userDao.getAll().firstOrNull()?.size == 0) {
                "Delete failed"
            }
        }
    }

}