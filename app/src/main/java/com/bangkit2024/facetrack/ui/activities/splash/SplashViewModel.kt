package com.bangkit2024.facetrack.ui.activities.splash

import androidx.lifecycle.ViewModel
import com.bangkit2024.facetrack.data.repository.AuthRepository

class SplashViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    suspend fun isLoggedIn() =
        authRepository.isLoggedIn()

    suspend fun isUpdatedData() =
        authRepository.isUpdatedDataRegister()

}