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

@HiltWorker
class GetUserDetailWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val githubRepository: GithubRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return WorkerUtil.tryWork(EXTRA_ERROR_MESSAGE) {
            val response = githubRepository.getDetailRemote(
                inputData.getString(EXTRA_USERNAME) ?: throw IllegalStateException("Null username")
            )

            if (response.isSuccessful) {
                githubRepository.updateLocal(response.body()!!.toUserDb())
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
        }
    }

    companion object {
        const val EXTRA_ERROR_MESSAGE = "errMsg"
        const val EXTRA_USERNAME = "username"
    }

}