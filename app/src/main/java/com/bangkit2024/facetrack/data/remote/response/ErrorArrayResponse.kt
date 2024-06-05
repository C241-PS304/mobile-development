package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorArrayResponse(

	@field:SerializedName("errors")
	val errors: List<String?>? = null
)
