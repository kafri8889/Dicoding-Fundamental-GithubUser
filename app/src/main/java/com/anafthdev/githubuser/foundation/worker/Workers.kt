package com.anafthdev.githubuser.foundation.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.workDataOf

object Workers {

    fun searchWorker(query: String): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SearchWorker>()
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .setInputData(
                workDataOf(
                    SearchWorker.EXTRA_QUERY to query
                )
            )
            .build()
    }

}