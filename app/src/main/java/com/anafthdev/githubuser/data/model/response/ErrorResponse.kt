package com.anafthdev.githubuser.data.model.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@SerializedName("message")
	val message: String,

	@SerializedName("documentation_url")
	val documentationUrl: String,

	@SerializedName("errors")
	val errors: List<ErrorItemResponse>? = null
)
