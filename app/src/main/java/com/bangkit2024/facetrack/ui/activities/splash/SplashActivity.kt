package com.bangkit2024.facetrack.ui.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivitySplashBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.intro.IntroActivity
import com.bangkit2024.facetrack.ui.activities.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding
    private val splashViewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        lifecycleScope.launch {
            Glide.with(this@SplashActivity)
                .load(R.drawable.logo_skintrack)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .centerCrop()
                .into(splashBinding.ivLogo)

            delay(1500)

            startActivity(
                if (splashViewModel.isLoggedIn() && !splashViewModel.isUpdatedData()) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                }
//                else if (splashViewModel.isLoggedIn() && !splashViewModel.isUpdatedData()) {
//                    Intent(this@SplashActivity, InputProfileActivity::class.java)
//                }
                else {
                    Intent(this@SplashActivity, IntroActivity::class.java)
                }
            )
            finish()
        }
    }
}