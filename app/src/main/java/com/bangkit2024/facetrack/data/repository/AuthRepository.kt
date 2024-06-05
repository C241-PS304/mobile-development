package com.bangkit2024.facetrack.data.repository

import com.bangkit2024.facetrack.data.datastore.UserPreference
import com.bangkit2024.facetrack.data.remote.request.ChangePasswordBody
import com.bangkit2024.facetrack.data.remote.request.ConfirmOtpBody
import com.bangkit2024.facetrack.data.remote.request.ForgotPasswordBody
import com.bangkit2024.facetrack.data.remote.request.LoginRegisterBody
import com.bangkit2024.facetrack.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){

    suspend fun register(loginRegisterBody: LoginRegisterBody) =
        apiService.register(loginRegisterBody)

    suspend fun login(loginRegisterBody: LoginRegisterBody) =
        apiService.login(loginRegisterBody)

    suspend fun forgotPassword(forgotPasswordBody: ForgotPasswordBody) =
        apiService.checkEmailForForgotPassword(forgotPasswordBody)

    suspend fun isLoggedIn() =
        userPreference.getSession().first().isNotBlank()

    suspend fun setUserToken(token: String) =
        userPreference.saveSession(token)

    suspend fun logout() =
        userPreference.logout()

    suspend fun getUserToken() =
        userPreference.getSession().first()

    suspend fun isUpdatedDataRegister() =
        userPreference.isUpdatedData().first()

    suspend fun updateRegisterUser(isUpdated: Boolean) =
        userPreference.updateRegisterUser(isUpdated)

    suspend fun confirmationOtp(confirmOtpBody: ConfirmOtpBody) =
        apiService.confirmOtp(confirmOtpBody)

    suspend fun changePassword(changePasswordBody: ChangePasswordBody) =
        apiService.changePassword(changePasswordBody)

    suspend fun getCurrentUser(token: String) =
        apiService.getCurrentUser(token)

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(
                    apiService,
                    userPreference
                ).also {
                    INSTANCE = it
                }
            }
        }
    }

}