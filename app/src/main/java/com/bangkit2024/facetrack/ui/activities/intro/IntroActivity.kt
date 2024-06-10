package com.bangkit2024.facetrack.ui.activities.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityIntroBinding
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity
import com.bangkit2024.facetrack.utils.showToast

class IntroActivity : AppCompatActivity() {

    private lateinit var introBinding: ActivityIntroBinding

    private var pressedTime = 0L

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                showToast(this@IntroActivity, "Tekan lagi untuk keluar")
            }

            pressedTime = System.currentTimeMillis()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        onBackPressedDispatcher.addCallback(this, backCallback)

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