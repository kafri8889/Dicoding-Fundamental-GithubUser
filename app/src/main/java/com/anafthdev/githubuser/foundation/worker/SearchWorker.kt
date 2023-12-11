package com.anafthdev.githubuser.foundation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.anafthdev.githubuser.data.model.response.ErrorResponse
import com.anafthdev.githubuser.data.model.response.SearchResponse
import com.anafthdev.githubuser.data.repository.GithubRepository
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.net.SocketTimeoutException

@HiltWorker
class SearchWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val githubRepository: GithubRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val response = githubRepository.searchRemote(inputData.getString(EXTRA_QUERY) ?: throw IllegalArgumentException("Null query"))

            if (response.isSuccessful) {
                Result.success(
                    workDataOf(
                        EXTRA_OUTPUT to response.body()!!
                    )
                )
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
            Result.retry()
        } catch (e: Exception) {
            Result.failure(
                workDataOf(
                    EXTRA_ERROR_MESSAGE to (e.message ?: "")
                )
            )
        }
    }

    companion object {
        const val EXTRA_QUERY = "query"
        const val EXTRA_ERROR_MESSAGE = "errMsg"

        /**
         * Return [SearchResponse]
         */
        const val EXTRA_OUTPUT = "output"
    }

}