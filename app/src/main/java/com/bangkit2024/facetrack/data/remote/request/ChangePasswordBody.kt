package com.bangkit2024.facetrack.data.remote.request

data class ChangePasswordBody(
    val email: String,
    val newPassword: String
)
