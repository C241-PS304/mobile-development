package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllProgramResponse(

	@field:SerializedName("data")
	val data: List<DataItemProgram?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)

data class SkincareItem(

	@field:SerializedName("skincareId")
	val skincareId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null
)

data class DataItemProgram(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doneAt")
	val doneAt: Any? = null,

	@field:SerializedName("skincare")
	val skincare: List<SkincareItem?>? = null,

	@field:SerializedName("namaProgram")
	val namaProgram: String? = null,

	@field:SerializedName("scan")
	val scan: List<Any?>? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("isDone")
	val isDone: Boolean? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("programId")
	val programId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
