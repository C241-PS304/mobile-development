package com.bangkit2024.facetrack.data.remote.request

import com.bangkit2024.facetrack.model.Skincare

data class NewProgramBody(
    val namaProgram: String,
    val skincare: List<Skincare>
)
