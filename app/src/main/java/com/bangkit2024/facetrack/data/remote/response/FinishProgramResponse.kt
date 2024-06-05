package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class FinishProgramResponse(

	@field:SerializedName("data")
	val data: DataFinishProgram? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class NumberOfProblemsItemFinishProgram(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("problemNumberId")
	val problemNumberId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ScanItemFinishProgram(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("scanId")
	val scanId: Int? = null,

	@field:SerializedName("numberOfProblems")
	val numberOfProblems: List<NumberOfProblemsItemFinishProgram?>? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class SkincareItemFinishProgram(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("skincareId")
	val skincareId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataFinishProgram(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doneAt")
	val doneAt: String? = null,

	@field:SerializedName("skincare")
	val skincare: List<SkincareItemFinishProgram?>? = null,

	@field:SerializedName("namaProgram")
	val namaProgram: String? = null,

	@field:SerializedName("scan")
	val scan: List<ScanItemFinishProgram?>? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("done")
	val done: Boolean? = null,

	@field:SerializedName("programId")
	val programId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
