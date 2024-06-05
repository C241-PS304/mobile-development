package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: DataScan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class NumberOfProblemsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("problemNumberId")
	val problemNumberId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataScan(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("scanId")
	val scanId: Int? = null,

	@field:SerializedName("numberOfProblems")
	val numberOfProblems: List<NumberOfProblemsItem?>? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
