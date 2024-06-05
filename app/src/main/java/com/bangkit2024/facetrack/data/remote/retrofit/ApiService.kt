package com.bangkit2024.facetrack.data.remote.retrofit

import com.bangkit2024.facetrack.data.remote.request.ChangePasswordBody
import com.bangkit2024.facetrack.data.remote.request.ConfirmOtpBody
import com.bangkit2024.facetrack.data.remote.request.ForgotPasswordBody
import com.bangkit2024.facetrack.data.remote.request.LoginRegisterBody
import com.bangkit2024.facetrack.data.remote.request.NewProgramBody
import com.bangkit2024.facetrack.data.remote.request.UserProfileBody
import com.bangkit2024.facetrack.data.remote.response.AllProblemResponse
import com.bangkit2024.facetrack.data.remote.response.AllProgramResponse
import com.bangkit2024.facetrack.data.remote.response.ChangePasswordResponse
import com.bangkit2024.facetrack.data.remote.response.ConfirmOTPResponse
import com.bangkit2024.facetrack.data.remote.response.CurrentUserResponse
import com.bangkit2024.facetrack.data.remote.response.DetailProgramResponse
import com.bangkit2024.facetrack.data.remote.response.FinishProgramResponse
import com.bangkit2024.facetrack.data.remote.response.ForgotPasswordResponse
import com.bangkit2024.facetrack.data.remote.response.LoginResponse
import com.bangkit2024.facetrack.data.remote.response.NewProgramResponse
import com.bangkit2024.facetrack.data.remote.response.ProgramAvailabilityResponse
import com.bangkit2024.facetrack.data.remote.response.RegisterResponse
import com.bangkit2024.facetrack.data.remote.response.ScanResponse
import com.bangkit2024.facetrack.data.remote.response.UpdateRegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // ====================== AUTHENTICATION =========================

    // Login User
    @POST("auth/login")
    suspend fun login(
        @Body registerRequest: LoginRegisterBody
    ) : LoginResponse

    // Register User
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: LoginRegisterBody
    ) : RegisterResponse

    // Update data user after Register
    @PUT("auth/register/update/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body userProfileBody: UserProfileBody // (MALE/FEMALE)
    ) : UpdateRegisterResponse

    // Forgot Password
    @POST("auth/forgot-password")
    suspend fun checkEmailForForgotPassword(
        @Body forgotPasswordBody: ForgotPasswordBody
    ) : ForgotPasswordResponse

    // Confirm OTP
    @POST("auth/confirm-otp")
    suspend fun confirmOtp(
        @Body confirmOTPBody: ConfirmOtpBody
    ) : ConfirmOTPResponse

    // Change password
    @POST("auth/change-password")
    suspend fun changePassword(
        @Body changePasswordBody: ChangePasswordBody
    ) : ChangePasswordResponse

    // ====================== PROGRAMS =========================

    // Get All Program
    @GET("programs")
    suspend fun getPrograms(
        @Header("Authorization") token: String,
    ) : AllProgramResponse

    // Add new program
    @POST("programs")
    suspend fun addProgram(
        @Header("Authorization") token: String,
        @Body newProgramBody: NewProgramBody
    ) : NewProgramResponse

    // Get Detail Program
    @GET("programs/{id}")
    suspend fun getDetailProgram(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : DetailProgramResponse

    // Finish Program
    @PUT("programs/{id}")
    suspend fun updateStatusProgram(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : FinishProgramResponse

    // Program availability
    @GET("programs/availability")
    suspend fun getProgramAvailability(
        @Header("Authorization") token: String,
    ) : ProgramAvailabilityResponse

    // ====================== SCAN =========================

    // Add Scan
    @Multipart
    @POST("scans")
    suspend fun addScan(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("programId") programId: Int,
        @Part("problemId") problemId: String,
        @Part("jumlah") jumlah: String
    ) : ScanResponse

    // ====================== PROBLEM =========================

    // Get All Problem
    @GET("problems")
    suspend fun getProblems(
        @Header("Authorization") token: String
    ) : AllProblemResponse

    // ====================== USER =========================

    // Get User Data
    @GET("auth/user/current")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ) : CurrentUserResponse

}