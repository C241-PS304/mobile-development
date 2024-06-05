package com.bangkit2024.facetrack.data.repository

import com.bangkit2024.facetrack.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.http.Multipart

class UserRepository private constructor(
    private val apiService: ApiService
){

    // Update user data
    suspend fun updateUser(id: String, nama: String, noTelp: String) =
        apiService.updateUser(id, nama, noTelp)


    // Get all program
    suspend fun getAllPrograms(token: String) =
        apiService.getPrograms(token)

    // Add new program
    suspend fun addProgram(token: String, namaProgram: String, skincares: String) =
        apiService.addProgram(token, namaProgram, skincares)

    // Update status program
    suspend fun updateStatusProgram(token: String, id: String, isActive: String) =
        apiService.updateStatusProgram(token, id, isActive)

    // Get detail program
    suspend fun getDetailProgram(token: String, id: String) =
        apiService.getDetailProgram(token, id)

    // Add new scan
    suspend fun addScan(token: String, gambar: MultipartBody.Part) =
        apiService.addScan(token, gambar)

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(
                    apiService
                ).also {
                    INSTANCE = it
                }
            }
        }
    }
}