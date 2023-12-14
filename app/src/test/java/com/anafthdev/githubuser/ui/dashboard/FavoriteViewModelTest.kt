package com.anafthdev.githubuser.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anafthdev.githubuser.data.model.db.UserDb
import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.model.response.UserResponse
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.ui.dashboard.extension.InstantExecutorExtension
import com.anafthdev.githubuser.ui.favorite.FavoriteViewModel
import com.jraska.livedata.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Response

@ExtendWith(InstantExecutorExtension::class)
class FavoriteViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favoriteViewModel: FavoriteViewModel

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(SupervisorJob() + dispatcher)

    private val users = flowOf(
        listOf(
            UserDb("kafri", 0, 12, 65, isFavorite = false),
            UserDb("kafri8889", 1, 200, 100, isFavorite = true)
        )
    )

    private val githubRepository = object : GithubRepository {
        override suspend fun searchRemote(query: String): Response<SearchResponse> = Response.success(null)
        override suspend fun getUsersRemote(): Response<List<UserResponse>> = Response.success(null)
        override suspend fun getDetailRemote(username: String): Response<UserResponse> = Response.success(null)
        override suspend fun getFollowersRemote(username: String): Response<List<UserResponse>> = Response.success(null)
        override suspend fun getFollowingRemote(username: String): Response<List<UserResponse>> = Response.success(null)
        override fun getAllUserLocal(): Flow<List<UserDb>> = emptyFlow()
        override fun getFavoriteUserLocal(): Flow<List<UserDb>> = flow {
            emit(users.first().filter { it.isFavorite })
        }
        override fun getUserByUsername(username: String): Flow<UserDb?> = emptyFlow()
        override suspend fun updateLocal(vararg userDb: UserDb) {}
        override suspend fun deleteLocal(vararg userDb: UserDb) {}
        override suspend fun insertLocal(vararg userDb: UserDb) {}

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)

        favoriteViewModel = FavoriteViewModel(githubRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test() = runTest{
        testScope.launch {
            favoriteViewModel.state.test()
                .awaitValue()
                .assertValue { it.users.size == 1 }
        }
    }
}