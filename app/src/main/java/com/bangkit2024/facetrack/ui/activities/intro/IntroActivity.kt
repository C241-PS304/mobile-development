package com.bangkit2024.facetrack.ui.activities.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityIntroBinding
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity

class IntroActivity : AppCompatActivity() {

    private lateinit var introBinding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        introBinding.btnNextIntro3.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            // Intent to LoginActivity
            //
            // startActivity(Intent(this, LoginActivity::class.java))
            // finish()
        }
    }
}