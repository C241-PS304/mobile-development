package com.bangkit2024.facetrack.data.remote.response

import com.google.gson.annotations.SerializedName

data class CurrentUserResponse(

	@field:SerializedName("data")
	val data: CurrentUserData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class SkincareItemCurrentUser(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("skincareId")
	val skincareId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class NumberOfProblemsItemCurrentUser(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("problemNumberId")
	val problemNumberId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ProgramItemCurrentUser(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doneAt")
	val doneAt: Any? = null,

	@field:SerializedName("skincare")
	val skincare: List<SkincareItemCurrentUser?>? = null,

	@field:SerializedName("namaProgram")
	val namaProgram: String? = null,

	@field:SerializedName("scan")
	val scan: List<ScanItemCurrentUser?>? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("done")
	val done: Boolean? = null,

	@field:SerializedName("programId")
	val programId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class CurrentUserData(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("program")
	val program: List<ProgramItemCurrentUser?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)

data class ScanItemCurrentUser(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("scanId")
	val scanId: Int? = null,

	@field:SerializedName("numberOfProblems")
	val numberOfProblems: List<NumberOfProblemsItemCurrentUser?>? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
