package com.anafthdev.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val users: List<User>,
    @SerializedName("total_count")
    val count: Int
)