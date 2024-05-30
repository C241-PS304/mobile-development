package com.bangkit2024.facetrack.ui.activities.inputprofile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityInputProfileBinding
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity
import com.bangkit2024.facetrack.ui.activities.main.MainActivity

class InputProfileActivity : AppCompatActivity() {
    private lateinit var inputProfileBinding: ActivityInputProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inputProfileBinding = ActivityInputProfileBinding.inflate(layoutInflater)
        setContentView(inputProfileBinding.root)

        setupView()
        setupAction()

    }

    private fun setupView() {

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupAction(){
        inputProfileBinding.apply {
            inputButton.setOnClickListener {
                val nama = etName.text.toString()
                val phone = etPhone.text.toString()
                val gender = etGender.text.toString()
                startActivity(Intent(this@InputProfileActivity, MainActivity::class.java))
            }
        }
    }
}