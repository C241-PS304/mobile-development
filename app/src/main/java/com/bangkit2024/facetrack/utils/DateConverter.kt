package com.bangkit2024.facetrack.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun ubahFormatTanggal(tanggal: String): String {
    val zonedDateTime = ZonedDateTime.parse(tanggal)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
    return zonedDateTime.format(formatter)
}