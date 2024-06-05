package com.bangkit2024.facetrack.ui.activities.detailProgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityDetailProgramBinding

class DetailProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProgramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}