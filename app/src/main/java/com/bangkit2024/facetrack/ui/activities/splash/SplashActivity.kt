package com.bangkit2024.facetrack.ui.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivitySplashBinding
import com.bangkit2024.facetrack.ui.activities.intro.IntroActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        Glide.with(this)
            .load(R.raw.skin_track)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(splashBinding.ivLogo)

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