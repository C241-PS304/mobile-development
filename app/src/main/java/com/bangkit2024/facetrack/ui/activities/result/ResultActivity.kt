package com.bangkit2024.facetrack.ui.activities.result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityResultBinding
import com.bangkit2024.facetrack.databinding.ActivityScanResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var resultBinding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)
    }
}