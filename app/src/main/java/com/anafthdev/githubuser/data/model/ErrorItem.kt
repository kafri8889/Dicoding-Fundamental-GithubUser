package com.anafthdev.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class ErrorItem(

    @SerializedName("code")
    val code: String,

    @SerializedName("field")
    val field: String,

    @SerializedName("resource")
    val resource: String
)
