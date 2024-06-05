package com.bangkit2024.facetrack.ui.activities.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.LoginRegisterBody
import com.bangkit2024.facetrack.databinding.ActivityRegisterBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.inputProfile.InputProfileActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.hideKeyboard
import com.bangkit2024.facetrack.utils.showToast

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        registerBinding.apply {
            emailEditTextLayout.setEditText(etRegistEmail)
            passwordEditTextLayout.setEditText(etRegistPassword)
        }
    }

    private fun setupAction() {
        registerBinding.apply {

            // Register Button
            registerButton.setOnClickListener {
                hideKeyboard(this@RegisterActivity, etRegistCpass)
                val email = etRegistEmail.text.toString()
                val password = etRegistPassword.text.toString()
                val cpass = etRegistCpass.text.toString()

                if (checkValidation(email, password, cpass)) {
                    val loginRegisterBody = LoginRegisterBody(email, password)
                    registerViewModel.register(loginRegisterBody)
                    setupStateRegister()
                }
            }

            // Navigate to Login
            tvToLogin.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            // Back Button
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setupStateRegister() {
        registerViewModel.stateRegister.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)

                        if (result.data?.id.toString().isNotEmpty()) {
                            val intentToInputProfile = Intent(this, InputProfileActivity::class.java)
                            intentToInputProfile.putExtra(
                                InputProfileActivity.EXTRA_ID_USER,
                                result.data?.id
                            )
                            intentToInputProfile.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intentToInputProfile)
                            finish()
                        }
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
        registerBinding.apply {
            if (isLoading) {
                registerButton.text = ""
                progressBar.visibility = View.VISIBLE
                registerButton.isEnabled = false
                tvToLogin.isEnabled = false
            } else {
                registerButton.text = getString(R.string.login)
                progressBar.visibility = View.GONE
                registerButton.isEnabled = true
                tvToLogin.isEnabled = true
            }
        }
    }

    private fun checkValidation(email: String, pass: String, cpass: String): Boolean {
        registerBinding.apply {
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etRegistEmail.error = "Masukkan Email Anda dengan benar"
                etRegistEmail.requestFocus()
            } else if (pass.isEmpty() || pass.length < 8) {
                etRegistPassword.error = "Masukkan Password Anda dengan benar"
                etRegistPassword.requestFocus()
            } else if (cpass.isEmpty() || cpass.length < 8) {
                etRegistCpass.error = "Masukkan Konfirmasi Password Anda dengan benar"
                etRegistCpass.requestFocus()
            } else if (pass != cpass) {
                etRegistPassword.error = "Password Anda Salah"
                etRegistCpass.error = "Konfirmasi Password Anda Salah"
                etRegistPassword.requestFocus()
                etRegistCpass.requestFocus()
            } else {
                return true
            }
            return false
        }
    }

    private fun successDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setPositiveButton("Lanjutkan") { _, _ ->
                val intent = Intent(this@RegisterActivity, InputProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun failedDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setPositiveButton("Kembali") { _, _ -> }
            create()
            show()
        }
    }
}