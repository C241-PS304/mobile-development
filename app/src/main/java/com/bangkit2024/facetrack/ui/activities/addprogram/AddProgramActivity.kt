package com.bangkit2024.facetrack.ui.activities.addprogram

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityAddProgramBinding
import com.bangkit2024.facetrack.databinding.ActivityInputProfileBinding

class AddProgramActivity : AppCompatActivity() {
    private lateinit var addProgramBinding: ActivityAddProgramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addProgramBinding = ActivityAddProgramBinding.inflate(layoutInflater)
        setContentView(addProgramBinding.root)

    }
}