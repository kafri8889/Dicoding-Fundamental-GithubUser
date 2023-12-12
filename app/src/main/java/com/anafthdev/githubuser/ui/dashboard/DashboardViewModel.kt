package com.anafthdev.githubuser.ui.dashboard

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.anafthdev.githubuser.foundation.extension.toUser
import com.anafthdev.githubuser.foundation.worker.GetRemoteUsersWorker
import com.anafthdev.githubuser.foundation.worker.SearchUserWorker
import com.anafthdev.githubuser.foundation.worker.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val githubRepository: GithubRepository
): BaseViewModel<DashboardState>(DashboardState()) {

    private val currentSearchUserWorkerId = MutableStateFlow<UUID?>(null)
    private val currentGetRemoteUserWorkerId = MutableStateFlow<UUID?>(null)
    private val currentQuery = MutableStateFlow("")

    init {
        loadUsers()

        viewModelScope.launch {
            githubRepository.getAllUserLocal().combine(currentQuery) { users, query ->
                users to query
            }.collectLatest { (users, query) ->
                updateState {
                    copy(
                        users = users
                            .filter { it.login.contains(query) }
                            .map { it.toUser() }
                    )
                }
            }
        }

        viewModelScope.launch {
            currentSearchUserWorkerId.filterNotNull().flatMapLatest { id ->
                workManager.getWorkInfoByIdFlow(id)
            }.collectLatest { workInfo ->
                updateState {
                    when (workInfo.state) {
                        WorkInfo.State.FAILED, WorkInfo.State.SUCCEEDED -> {
                            copy(
                                isLoading = false,
                                errorMsg = workInfo.outputData.getString(SearchUserWorker.EXTRA_ERROR_MESSAGE) ?: ""
                            )
                        }
                        else -> this
                    }
                }
            }
        }

        viewModelScope.launch {
            currentGetRemoteUserWorkerId.filterNotNull().flatMapLatest { id ->
                workManager.getWorkInfoByIdFlow(id)
            }.collectLatest { workInfo ->
                updateState {
                    when (workInfo.state) {
                        WorkInfo.State.FAILED, WorkInfo.State.SUCCEEDED -> {
                            copy(
                                isLoading = false,
                                errorMsg = workInfo.outputData.getString(GetRemoteUsersWorker.EXTRA_ERROR_MESSAGE) ?: ""
                            )
                        }
                        else -> this
                    }
                }
            }
        }
    }

    private fun loadUsers() {
        // Jika saat ini sedang me-load users, batalkan sebelum me-load lagi
        currentGetRemoteUserWorkerId.value?.let(workManager::cancelWorkById)

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        viewModelScope.launch {
            workManager.enqueue(
                Workers.getRemoteUserWorker().also {
                    currentGetRemoteUserWorkerId.emit(it.id)
                }
            )
        }
    }

    fun search(query: String) {
        // Cancel current search work jika ada, sebelum mencari dengan query baru
        currentSearchUserWorkerId.value?.let(workManager::cancelWorkById)

        viewModelScope.launch {
            currentQuery.emit(query)

            // Jika query kosong, load random user
            if (query.isBlank()) {
                loadUsers()
                return@launch
            }

            updateState {
                copy(
                    isLoading = true,
                    errorMsg = "" // Reset pesan error
                )
            }

            workManager.enqueue(
                Workers.searchUserWorker(query).also {
                    currentSearchUserWorkerId.emit(it.id)
                }
            )
        }
    }

}