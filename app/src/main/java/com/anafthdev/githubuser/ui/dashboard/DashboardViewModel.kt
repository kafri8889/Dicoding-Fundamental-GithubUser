package com.anafthdev.githubuser.ui.dashboard

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.anafthdev.githubuser.foundation.extension.toUser
import com.anafthdev.githubuser.foundation.worker.SearchWorker
import com.anafthdev.githubuser.foundation.worker.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val workManager: WorkManager
): BaseViewModel<DashboardState>(DashboardState()) {

    private val currentSearchWorkerId = MutableStateFlow<UUID?>(null)

    init {
        loadUsers()

        viewModelScope.launch {
            currentSearchWorkerId.filterNotNull().flatMapLatest { id ->
                workManager.getWorkInfoByIdFlow(id)
            }.collectLatest { workInfo ->
                updateState {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val response = workInfo.outputData.keyValueMap[SearchWorker.EXTRA_OUTPUT] as SearchResponse

                            copy(
                                isLoading = false,
                                errorMsg = "",
                                users = response.users.map { it.toUser() }
                            )
                        }
                        WorkInfo.State.FAILED -> {
                            copy(
                                isLoading = false,
                                errorMsg = workInfo.outputData.getString(SearchWorker.EXTRA_ERROR_MESSAGE) ?: ""
                            )
                        }
                        else -> this
                    }
                }
            }
        }
    }

    private fun loadUsers() {
        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }
    }

    fun search(query: String) {
        // Cancel current search work jika ada, sebelum mencari dengan query baru
        currentSearchWorkerId.value?.let(workManager::cancelWorkById)

        // Jika query kosong, load random user
        if (query.isBlank()) {
            loadUsers()
            return
        }

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        viewModelScope.launch {
            workManager.enqueue(
                Workers.searchWorker(query).also {
                    currentSearchWorkerId.emit(it.id)
                }
            )
        }
    }

}