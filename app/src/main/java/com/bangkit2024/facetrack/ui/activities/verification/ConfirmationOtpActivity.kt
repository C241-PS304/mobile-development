package com.bangkit2024.facetrack.ui.activities.verification

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.ConfirmOtpBody
import com.bangkit2024.facetrack.data.remote.request.ForgotPasswordBody
import com.bangkit2024.facetrack.databinding.ActivityConfirmationOtpBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.newPassword.NewPasswordActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.hideKeyboard
import com.bangkit2024.facetrack.utils.showToast

class ConfirmationOtpActivity : AppCompatActivity() {

    private lateinit var confirmationOtpBinding: ActivityConfirmationOtpBinding
    private val confirmationOtpViewModel by viewModels<ConfirmatioOtpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confirmationOtpBinding = ActivityConfirmationOtpBinding.inflate(layoutInflater)
        setContentView(confirmationOtpBinding.root)

        if (intent != null) {
            email = intent.getStringExtra(EXTRA_EMAIL).toString()
        }

        setupViews()
        setupOtpInputs()
        setupObserver()
        setupAction(email)
    }

    private fun setupViews() {
        confirmationOtpBinding.apply {
            tvEmail.text = email
        }
    }

    private fun setupObserver() {
        confirmationOtpViewModel.timeLeft.observe(this) { timeLeft ->
            confirmationOtpBinding.btnKirimUlang.text = timeLeft.toString()

            if (timeLeft == 0L) {
                confirmationOtpBinding.btnKirimUlang.text = getString(R.string.kirim_ulang)
            } else {
                val textKirimUlang = "Kirim ulang dalam $timeLeft detik"
                confirmationOtpBinding.btnKirimUlang.text = textKirimUlang
            }
        }

        confirmationOtpViewModel.isButtonEnabled.observe(this) { isEnabled ->
            confirmationOtpBinding.btnKirimUlang.isEnabled = isEnabled
        }
    }

    private fun setupAction(email: String) {
        confirmationOtpBinding.apply {
            btnVerifikasi.setOnClickListener {
                val confirmOtpBody = ConfirmOtpBody(
                    email = email,
                    otp = etCode1.text.toString() + etCode2.text.toString() + etCode3.text.toString() + etCode4.text.toString()
                )
                confirmationOtpViewModel.verification(confirmOtpBody)
                setupConfirmationState()
            }

            btnKirimUlang.setOnClickListener {
                val forgotPasswordBody = ForgotPasswordBody(email)
                confirmationOtpViewModel.resendOtp(forgotPasswordBody)
                setupResendOtpState()
            }
        }
    }

    private fun setupConfirmationState() {
        confirmationOtpViewModel.stateVerification.observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoadingVerifikasi(true)
                    }
                    is Result.Success -> {
                        showLoadingVerifikasi(false)
                        val intentToNewPassword = Intent(this, NewPasswordActivity::class.java)
                        intentToNewPassword.putExtra(NewPasswordActivity.EXTRA_EMAIL, email)
                        startActivity(intentToNewPassword)
                    }
                    is Result.Error -> {
                        showLoadingVerifikasi(false)
                        showToast(this, result.error)
                    }
                }
            }
        }
    }

    private fun setupResendOtpState() {
        confirmationOtpViewModel.resetPasswordState.observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoadingResend(true)
                    }
                    is Result.Success -> {
                        showLoadingResend(false)
                        confirmationOtpViewModel.startCountdownTimer()
                        showToast(this, "Berhasil mengirim ulang ke email kamu")
                    }
                    is Result.Error -> {
                        showLoadingResend(false)
                        showToast(this, result.error)
                    }
                }
            }
        }
    }

    private fun showLoadingVerifikasi(isLoading: Boolean) {
        confirmationOtpBinding.apply {
            if (isLoading) {
                btnVerifikasi.text = ""
                progressBarVerifikasi.visibility = View.VISIBLE
                btnVerifikasi.isEnabled = false
            } else {
                btnVerifikasi.text = getString(R.string.verifikasi)
                progressBarVerifikasi.visibility = View.GONE
                btnVerifikasi.isEnabled = true
            }
        }
    }

    private fun showLoadingResend(isLoading: Boolean) {
        confirmationOtpBinding.apply {
            if (isLoading) {
                btnKirimUlang.text = ""
                progressBarKirimUlang.visibility = View.VISIBLE
                btnKirimUlang.isEnabled = false
            } else {
                btnKirimUlang.text = getString(R.string.kirim_ulang)
                progressBarKirimUlang.visibility = View.GONE
                btnKirimUlang.isEnabled = true
            }
        }
    }

    private fun setupOtpInputs() {
        confirmationOtpBinding.etCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    confirmationOtpBinding.etCode2.requestFocus()
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
        confirmationOtpBinding.etCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    confirmationOtpBinding.etCode3.requestFocus()
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
        confirmationOtpBinding.etCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    confirmationOtpBinding.etCode4.requestFocus()
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
        confirmationOtpBinding.etCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {
                if (value.toString().trim().isNotEmpty()) {
                    hideKeyboard(this@ConfirmationOtpActivity, confirmationOtpBinding.etCode4)
                }
            }

            override fun afterTextChanged(value: Editable?) {}
        })
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}