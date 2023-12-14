package com.anafthdev.githubuser.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anafthdev.githubuser.data.model.UserPreference
import com.anafthdev.githubuser.data.repository.UserPreferenceRepository
import com.anafthdev.githubuser.ui.dashboard.extension.InstantExecutorExtension
import com.anafthdev.githubuser.ui.setting.SettingViewModel
import com.jraska.livedata.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
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

@ExtendWith(InstantExecutorExtension::class)
class SettingViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var settingViewModel: SettingViewModel

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(SupervisorJob() + dispatcher)

    private val pref = MutableStateFlow(UserPreference())

    private val userPreferenceRepository = object : UserPreferenceRepository {
        override val getUserPreference: Flow<UserPreference>
            get() = pref

        override suspend fun setIsDarkTheme(value: Boolean) {
            pref.update {
                it.copy(
                    isDarkTheme = value
                )
            }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        settingViewModel = SettingViewModel(userPreferenceRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set to dark theme`() = runTest {
        testScope.launch {
            // Await until job complete
            settingViewModel.setDarkTheme(true).join()
            settingViewModel.state.test()
                .awaitValue()
                .assertValue { it.isDarkTheme }
        }
    }

    @Test
    fun `set to light theme`() = runTest {
        testScope.launch {
            // Await until job complete
            settingViewModel.setDarkTheme(false).join()
            settingViewModel.state.test()
                .awaitValue()
                .assertValue { !it.isDarkTheme }
        }
    }

}