package com.bangkit2024.facetrack.di

import android.content.Context
import com.bangkit2024.facetrack.data.datastore.UserPreference
import com.bangkit2024.facetrack.data.datastore.datastore
import com.bangkit2024.facetrack.data.remote.retrofit.ApiConfig
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository

object Injection {

    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.datastore)
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService, userPreference)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val userPreference = UserPreference.getInstance(context.datastore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, userPreference)
    }
}