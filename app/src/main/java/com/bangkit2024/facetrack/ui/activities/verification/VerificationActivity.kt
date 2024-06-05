package com.bangkit2024.facetrack.ui.activities.verification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityVerificationBinding
import com.bangkit2024.facetrack.ui.activities.newPassword.NewPasswordActivity
import com.bangkit2024.facetrack.utils.showToast

class VerificationActivity : AppCompatActivity() {

    private lateinit var verificationBinding: ActivityVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verificationBinding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(verificationBinding.root)

        setupOtpInputs()

        verificationBinding.btnKirimUlang.setOnClickListener {
            showToast(this, "Berhasil mengirim ulang ke email kamu")
            verificationBinding.btnKirimUlang.isEnabled = false

            // Create timer for 30 seconds (In the ViewModel)
        }

        verificationBinding.btnVerifikasi.setOnClickListener {
            // Temporary
            startActivity(Intent(this, NewPasswordActivity::class.java))

            // Process to API

        }
    }

    private fun setupOtpInputs() {
        verificationBinding.etCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    verificationBinding.etCode2.requestFocus()
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
        verificationBinding.etCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    verificationBinding.etCode3.requestFocus()
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
        verificationBinding.etCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    verificationBinding.etCode4.requestFocus()
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
        verificationBinding.etCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(verificationBinding.etCode4.windowToken, 0)
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
    }
}