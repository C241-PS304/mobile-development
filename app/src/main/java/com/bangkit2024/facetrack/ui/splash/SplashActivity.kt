package com.bangkit2024.facetrack.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivitySplashBinding
import com.bangkit2024.facetrack.ui.intro.IntroActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        // This is temporary solution before implement API and Store token
        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
            finish()
        }

        // After implement API and Store token
        /*
        * Show image logo using Glide
        * Delay 3 second
        * Check :
        *       if user has loggedIn
        *           Intent to HomeActivity
        *       else
        *           Intent to IntroActivity
        *  Finish this activity
        * */
    }
}