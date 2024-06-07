package com.bangkit2024.facetrack.ui.activities.scanResult

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityScanResultBinding
import com.bangkit2024.facetrack.ui.activities.main.MainActivity

class ScanResultActivity : AppCompatActivity() {
    private lateinit var scanResultBinding: ActivityScanResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanResultBinding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(scanResultBinding.root)

        scanResultBinding.btnSave.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Berhasil Tersimpan", Toast.LENGTH_SHORT).show()
        }

    }
}