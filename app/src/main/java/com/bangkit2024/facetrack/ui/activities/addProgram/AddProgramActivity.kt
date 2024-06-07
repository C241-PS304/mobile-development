package com.bangkit2024.facetrack.ui.activities.addProgram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityAddProgramBinding

class AddProgramActivity : AppCompatActivity() {

    private lateinit var addProgramBinding: ActivityAddProgramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addProgramBinding = ActivityAddProgramBinding.inflate(layoutInflater)
        setContentView(addProgramBinding.root)
    }
}