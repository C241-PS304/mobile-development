package com.bangkit2024.facetrack.ui.activities.detailProgram

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityDetailProgramBinding

class DetailProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProgramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}