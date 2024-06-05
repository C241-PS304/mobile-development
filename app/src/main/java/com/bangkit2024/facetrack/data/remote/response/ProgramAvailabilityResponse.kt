package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProgramAvailabilityResponse(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
