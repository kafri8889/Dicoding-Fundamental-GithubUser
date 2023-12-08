package com.anafthdev.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class License(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("spdx_id")
    val spdxId: String? = null,

    @SerializedName("key")
    val key: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("node_id")
    val nodeId: String? = null
)