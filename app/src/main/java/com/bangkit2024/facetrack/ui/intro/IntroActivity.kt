package com.bangkit2024.facetrack.ui.intro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var introBinding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        introBinding.btnNextIntro3.setOnClickListener {
            // Intent to LoginActivity
            //
            // startActivity(Intent(this, LoginActivity::class.java))
            // finish()
        }
    }
}