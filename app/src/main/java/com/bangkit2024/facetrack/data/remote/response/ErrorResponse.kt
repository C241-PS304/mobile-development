package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("errors")
	val errors: String? = null
)
