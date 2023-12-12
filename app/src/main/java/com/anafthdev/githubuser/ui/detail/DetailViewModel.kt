package com.anafthdev.githubuser.ui.detail

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.base.BaseViewModel
import com.anafthdev.githubuser.foundation.extension.toUser
import com.anafthdev.githubuser.foundation.worker.GetRemoteUsersWorker
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
class DetailViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val workManager: WorkManager
): BaseViewModel<DetailState>(DetailState()) {

    private val currentGetUserDetailWorkerId = MutableStateFlow<UUID?>(null)
    private val currentUsername = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            currentUsername.filterNotNull().flatMapLatest { username ->
                githubRepository.getUserByUsername(username).filterNotNull()
            }.collectLatest { user ->
                updateState {
                    copy(
                        user = user.toUser()
                    )
                }
            }
        }

        viewModelScope.launch {
            currentGetUserDetailWorkerId.filterNotNull().flatMapLatest { id ->
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

    fun getDetail(username: String) {
        if (username == state.value?.user?.login) return

        updateState {
            copy(
                isLoading = true,
                errorMsg = "" // Reset pesan error
            )
        }

        viewModelScope.launch {
            currentUsername.emit(username)
            workManager.enqueue(
                Workers.getUserDetailWorker(username).also {
                    currentGetUserDetailWorkerId.emit(it.id)
                }
            )
        }
    }

}