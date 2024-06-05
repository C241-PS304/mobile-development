package com.bangkit2024.facetrack.ui.activities.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bangkit2024.facetrack.databinding.ActivityIntroBinding
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity
import com.bangkit2024.facetrack.ui.adapters.IntroViewPagerAdapter
import com.bangkit2024.facetrack.utils.showToast

class IntroActivity : AppCompatActivity() {

    private lateinit var introBinding: ActivityIntroBinding
    private lateinit var switchButton: ViewPager2.OnPageChangeCallback

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

        setUpViewPager2()
    }

    private fun setUpViewPager2() {

        switchButton = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    introBinding.btnNext.visibility = View.GONE
                    introBinding.btnFinish.visibility = View.VISIBLE
                } else {
                    introBinding.btnNext.visibility = View.VISIBLE
                    introBinding.btnFinish.visibility = View.GONE
                }
            }
        }

        introBinding.viewPager.apply {
            adapter = IntroViewPagerAdapter(this@IntroActivity)
            introBinding.viewPager.registerOnPageChangeCallback(switchButton)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        introBinding.dotsIndicator.attachTo(introBinding.viewPager)

        introBinding.btnNext.setOnClickListener {
            if (introBinding.viewPager.currentItem < 2) {
                introBinding.viewPager.currentItem += 1
            }
        }

        introBinding.btnFinish.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}