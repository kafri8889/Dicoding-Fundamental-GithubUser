package com.anafthdev.githubuser.foundation.worker

import androidx.work.ListenableWorker
import androidx.work.workDataOf
import timber.log.Timber
import java.net.SocketTimeoutException

object WorkerUtil {

    inline fun tryWork(
        extraErrorMsg: String,
        block: () -> ListenableWorker.Result
    ): ListenableWorker.Result {
        return try {
            block()
        } catch (e: SocketTimeoutException) {
            Timber.e(e, e.message)
            ListenableWorker.Result.retry()
        } catch (e: Exception) {
            Timber.e(e, e.message)
            ListenableWorker.Result.failure(
                workDataOf(
                    extraErrorMsg to (e.message ?: "")
                )
            )
        }
    }

}