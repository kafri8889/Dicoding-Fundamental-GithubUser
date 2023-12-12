package com.anafthdev.githubuser.foundation.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.workDataOf

object Workers {

    fun searchUserWorker(query: String): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SearchUserWorker>()
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .setInputData(
                workDataOf(
                    SearchUserWorker.EXTRA_QUERY to query
                )
            )
            .build()
    }

    fun getRemoteUserWorker(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<GetRemoteUsersWorker>()
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .build()
    }

}