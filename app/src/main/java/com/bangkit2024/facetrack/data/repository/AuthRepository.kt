package com.bangkit2024.facetrack.data.repository

import com.bangkit2024.facetrack.data.remote.retrofit.ApiService

class AuthRepository private constructor(
    private val apiService: ApiService
){

    suspend fun register(email: String, password: String) =
        apiService.register(email, password)

    suspend fun login(email: String, password: String) =
        apiService.login(email, password)

    // Is Logged in
    // Clear Token
    // Get user token

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(apiService: ApiService): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(
                    apiService
                ).also {
                    INSTANCE = it
                }
            }
        }
    }

}