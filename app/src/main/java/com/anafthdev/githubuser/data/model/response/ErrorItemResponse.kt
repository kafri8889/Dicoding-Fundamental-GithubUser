package com.anafthdev.githubuser.data.model.response

import com.google.gson.annotations.SerializedName

data class ErrorItemResponse(

    @SerializedName("code")
    val code: String,

    @SerializedName("field")
    val field: String,

    @SerializedName("resource")
    val resource: String
)
