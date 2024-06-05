package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("data")
	val data: DataChangePassword? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataChangePassword(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nama")
	val nama: Any? = null,

	@field:SerializedName("gender")
	val gender: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("program")
	val program: List<Any?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("noTelp")
	val noTelp: Any? = null
)
