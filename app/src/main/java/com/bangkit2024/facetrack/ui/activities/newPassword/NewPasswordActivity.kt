package com.bangkit2024.facetrack.ui.activities.newPassword

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityNewPasswordBinding

class NewPasswordActivity : AppCompatActivity() {

    private lateinit var newPasswordBinding: ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newPasswordBinding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(newPasswordBinding.root)
    }
}