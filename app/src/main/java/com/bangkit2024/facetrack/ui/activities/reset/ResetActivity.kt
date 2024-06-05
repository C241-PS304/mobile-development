package com.bangkit2024.facetrack.ui.activities.reset

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.ForgotPasswordBody
import com.bangkit2024.facetrack.databinding.ActivityResetBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.verification.ConfirmationOtpActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.showToast

class ResetActivity : AppCompatActivity() {

    private lateinit var resetBinding: ActivityResetBinding
    private val resetViewModel by viewModels<ResetViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resetBinding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(resetBinding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        resetBinding.apply {
            emailEditTextLayout.setEditText(etEmail)
        }
    }

    private fun setupAction() {
        resetBinding.apply {

            // Send Email Button
            btnKirim.setOnClickListener {
                val email = etEmail.text.toString().trim()

                if (validateEmail(email)) {
                    val forgotPasswordBody = ForgotPasswordBody(email)
                    resetViewModel.resetPassword(forgotPasswordBody)
                    setupResetState(email)
                }
            }

            // Back Button
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        resetBinding.apply {
            if (email.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Masukkan Email dengan benar"
                etEmail.requestFocus()
                return false
            }
            return true
        }
    }

    private fun setupResetState(email: String) {
        resetViewModel.resetPasswordState.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val intentToVerification = Intent(this, ConfirmationOtpActivity::class.java)
                        intentToVerification.putExtra(ConfirmationOtpActivity.EXTRA_EMAIL, email)
                        startActivity(intentToVerification)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showToast(this, result.error)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        resetBinding.apply {
            if (isLoading) {
                btnKirim.text = ""
                progressBar.visibility = View.VISIBLE
                btnKirim.isEnabled = false
            } else {
                btnKirim.text = getString(R.string.login)
                progressBar.visibility = View.GONE
                btnKirim.isEnabled = true
            }
        }
    }

}
