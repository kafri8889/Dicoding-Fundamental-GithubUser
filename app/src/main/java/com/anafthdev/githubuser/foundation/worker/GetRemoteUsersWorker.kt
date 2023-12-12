package com.anafthdev.githubuser.foundation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.anafthdev.githubuser.data.model.response.ErrorResponse
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.anafthdev.githubuser.foundation.extension.toUserDb
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.net.SocketTimeoutException

@HiltWorker
class GetRemoteUsersWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val githubRepository: GithubRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val response = githubRepository.getUsersRemote()

            if (response.isSuccessful) {
                githubRepository.insertLocal(*response.body()!!.map { it.toUserDb() }.toTypedArray())
                Result.success()
            } else {
                val errMsg = response.errorBody().let {
                    if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
                    else ""
                }

                Result.failure(
                    workDataOf(
                        EXTRA_ERROR_MESSAGE to errMsg
                    )
                )
            }
        } catch (e: SocketTimeoutException) {
            Timber.e(e, e.message)
            Result.retry()
        } catch (e: Exception) {
            Timber.e(e, e.message)
            Result.failure(
                workDataOf(
                    EXTRA_ERROR_MESSAGE to (e.message ?: "")
                )
            )
        }
    }

    companion object {
        const val EXTRA_ERROR_MESSAGE = "errMsg"
    }
}