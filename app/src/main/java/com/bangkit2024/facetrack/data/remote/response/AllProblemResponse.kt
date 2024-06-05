package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllProblemResponse(

	@field:SerializedName("data")
	val data: List<DataItemProblem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemProblem(

	@field:SerializedName("saran")
	val saran: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("problemId")
	val problemId: Int? = null,

	@field:SerializedName("deskrpsi")
	val deskrpsi: String? = null
)
