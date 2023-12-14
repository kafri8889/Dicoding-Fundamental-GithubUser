package com.anafthdev.githubuser.ui.dashboard

import com.anafthdev.githubuser.data.model.db.UserDb
import com.anafthdev.githubuser.data.repository.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class GithubRepositoryTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var githubRepository: GithubRepository

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(SupervisorJob() + dispatcher)

    private val users = flowOf(
        listOf(
            UserDb("kafri", 0, 12, 65, isFavorite = false),
            UserDb("kafri8889", 1, 200, 100, isFavorite = true)
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get all user`() = runTest {
        Mockito.`when`(githubRepository.getAllUserLocal()).thenReturn(users)

        assert(githubRepository.getAllUserLocal().firstOrNull()?.size == 2)
    }

    @Test
    fun `get favorite user`() = runTest {
        Mockito.`when`(githubRepository.getAllUserLocal()).thenReturn(users)
        Mockito.`when`(githubRepository.getFavoriteUserLocal()).thenAnswer {
            githubRepository.getAllUserLocal().map { it.filter { it.isFavorite } }
        }

        assert(githubRepository.getFavoriteUserLocal().firstOrNull()?.size == 1)
    }

    @Test
    fun `input update delete get by username`() = runTest {
        testScope.launch {
            val flow = MutableStateFlow<List<UserDb>>(emptyList()).apply {
                emit(users.first())
            }

            Mockito.`when`(githubRepository.getAllUserLocal()).thenReturn(flow)

            Mockito.`when`(githubRepository.getUserByUsername("")).thenAnswer { iom ->
                githubRepository.getAllUserLocal().map { it.filter { it.login == (iom.arguments[0] as String) }.getOrNull(0) }
            }

            Mockito.`when`(githubRepository.insertLocal()).thenAnswer {
                flow.tryEmit(
                    flow.value.toMutableList().apply {
                        add(it.arguments[0] as UserDb)
                    }
                )
            }

            Mockito.`when`(githubRepository.updateLocal()).thenAnswer {
                val userDb = it.arguments[0] as UserDb

                flow.tryEmit(
                    flow.value.toMutableList().apply {
                        removeIf { it.login == userDb.login }
                        add(userDb)
                    }
                )
            }

            Mockito.`when`(githubRepository.deleteLocal()).thenAnswer {
                val userDb = it.arguments[0] as UserDb

                flow.tryEmit(
                    flow.value.toMutableList().apply {
                        removeIf { it.login == userDb.login }
                    }
                )
            }

            githubRepository.insertLocal(UserDb("wony", 2, 10_000_000, 10, isFavorite = true))

            assert(async { githubRepository.getAllUserLocal() }.await().last().size == 3) {
                githubRepository.getAllUserLocal().firstOrNull().let {
                    "Wrong output, excepted size 3 got: ${it?.size} | $it"
                }
            }

            githubRepository.updateLocal(UserDb("kafri8889", 1, 4253, 300, isFavorite = true))

            async { githubRepository.getUserByUsername("kafri8889") }.await().last().let {
                assert(it?.followers == 4253) {
                    "Excepted followers = 4253, got: ${it?.followers}"
                }
            }

            githubRepository.deleteLocal(users.first()[0])

            async { githubRepository.getUserByUsername("kafri") }.await().last().let {
                assert(it == null) {
                    "User not deleted, excepted null, got: $it"
                }
            }
        }
    }

}