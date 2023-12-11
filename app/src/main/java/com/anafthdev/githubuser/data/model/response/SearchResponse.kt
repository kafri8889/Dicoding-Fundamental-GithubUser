package com.anafthdev.githubuser.data.model.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val users: List<UserResponse>,
    @SerializedName("total_count")
    val count: Int
)