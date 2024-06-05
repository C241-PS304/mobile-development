package com.bangkit2024.facetrack.data.remote.request

data class ConfirmOtpBody(
    val email: String,
    val otp: String
)