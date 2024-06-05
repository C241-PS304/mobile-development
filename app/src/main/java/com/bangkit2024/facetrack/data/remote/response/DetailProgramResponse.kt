package com.bangkit2024.facetrack.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DetailProgramResponse(

	@field:SerializedName("data")
	val data: DataDetailProgram? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class ScanItem(

	@field:SerializedName("scanId")
	val scanId: Int? = null,

	@field:SerializedName("numberOfProblems")
	val numberOfProblems: List<NumberOfProblemsItemDetailProgram?>? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
) : Parcelable

@Parcelize
data class SkincareItemProgram(

	@field:SerializedName("skincareId")
	val skincareId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null
) : Parcelable

@Parcelize
data class Problem(

	@field:SerializedName("saran")
	val saran: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("problemId")
	val problemId: Int? = null,

	@field:SerializedName("deskrpsi")
	val deskrpsi: String? = null
) : Parcelable

@Parcelize
data class NumberOfProblemsItemDetailProgram(

	@field:SerializedName("problem")
	val problem: Problem? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("problemNumberId")
	val problemNumberId: Int? = null
) : Parcelable

@Parcelize
data class DataDetailProgram(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doneAt")
	val doneAt: String? = null,

	@field:SerializedName("skincare")
	val skincare: List<SkincareItemProgram?>? = null,

	@field:SerializedName("namaProgram")
	val namaProgram: String? = null,

	@field:SerializedName("scan")
	val scan: List<ScanItem?>? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("isDone")
	val isDone: Boolean? = null,

	@field:SerializedName("user")
	val user: UserDetailProgram? = null,

	@field:SerializedName("programId")
	val programId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class UserDetailProgram(

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
) : Parcelable
