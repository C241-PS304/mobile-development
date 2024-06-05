package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewProgramResponse(

	@field:SerializedName("data")
	val data: NewProgramData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class FullSkincareItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("skincareId")
	val skincareId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class NewProgramData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doneAt")
	val doneAt: Any? = null,

	@field:SerializedName("skincare")
	val skincare: List<FullSkincareItem?>? = null,

	@field:SerializedName("namaProgram")
	val namaProgram: String? = null,

	@field:SerializedName("scan")
	val scan: Any? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("done")
	val done: Boolean? = null,

	@field:SerializedName("programId")
	val programId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
