package com.bangkit2024.facetrack.ui.activities.reset

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityResetBinding
import com.bangkit2024.facetrack.ui.activities.verification.VerificationActivity

class ResetActivity : AppCompatActivity() {
    private lateinit var resetBinding: ActivityResetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resetBinding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(resetBinding.root)

        resetBinding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        resetBinding.btnKirim.setOnClickListener {
            startActivity(Intent(this, VerificationActivity::class.java))
        }

    }

}