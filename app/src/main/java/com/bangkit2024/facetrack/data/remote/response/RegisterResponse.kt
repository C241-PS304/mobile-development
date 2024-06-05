package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: DataRegister? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataRegister(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
