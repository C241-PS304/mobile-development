package com.bangkit2024.facetrack.data.repository

import com.bangkit2024.facetrack.data.datastore.UserPreference
import com.bangkit2024.facetrack.data.remote.request.NewProgramBody
import com.bangkit2024.facetrack.data.remote.request.UserProfileBody
import com.bangkit2024.facetrack.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){

    // Update user data
    suspend fun updateUser(id: Int, userProfileBody: UserProfileBody) =
        apiService.updateUser(id, userProfileBody)

    // Get all program
    suspend fun getAllPrograms(token: String) =
        apiService.getPrograms(token)

    // Get all problem
    suspend fun getAllProblems(token: String) = apiService.getProblems(token)

    // Check availibility
    suspend fun checkAvailability(token: String) =
        apiService.getProgramAvailability(token)

    // Add new program
    suspend fun addProgram(token: String, newProgramBody: NewProgramBody) =
        apiService.addProgram(token, newProgramBody)

    // Update status program
    suspend fun updateStatusProgram(token: String, id: Int) =
        apiService.updateStatusProgram(token, id)

    // Get detail program
    suspend fun getDetailProgram(token: String, id: Int) =
        apiService.getDetailProgram(token, id)

    //     Add new scan
    suspend fun addScan(token: String, multipartBody: MultipartBody.Part, programId: Int, problemId: String, jumlah: String) =
         apiService.addScan(token, multipartBody, programId, problemId, jumlah)


    //check program
    suspend fun checkProgram(token: String) = apiService.getProgramAvailability(token)

    suspend fun saveProgramId(id: String) = userPreference.saveIdProgramActive(id)

    suspend fun getProgramId() = userPreference.getIdProgramActive().first()

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(
                    apiService, userPreference
                ).also {
                    INSTANCE = it
                }
            }
        }
    }
}