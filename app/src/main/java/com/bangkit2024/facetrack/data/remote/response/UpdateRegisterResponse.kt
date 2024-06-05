package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateRegisterResponse(

	@field:SerializedName("data")
	val data: DataUpdateRegister? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataUpdateRegister(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("program")
	val program: List<Any?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)
