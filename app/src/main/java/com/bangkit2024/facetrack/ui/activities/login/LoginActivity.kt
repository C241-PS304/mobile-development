package com.bangkit2024.facetrack.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.LoginRegisterBody
import com.bangkit2024.facetrack.databinding.ActivityLoginBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.main.MainActivity
import com.bangkit2024.facetrack.ui.activities.register.RegisterActivity
import com.bangkit2024.facetrack.ui.activities.reset.ResetActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.hideKeyboard
import com.bangkit2024.facetrack.utils.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var pressedTime = 0L
    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                showToast(this@LoginActivity, "Tekan lagi untuk keluar")
            }

            pressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        onBackPressedDispatcher.addCallback(this, backCallback)

        setupView()
        setupAction()
    }

    private fun setupView() {
        loginBinding.apply {
            emailEditTextLayout.setEditText(etLoginEmail)
            passwordEditTextLayout.setEditText(etLoginPassword)
        }
    }

    private fun setupAction() {
        loginBinding.apply {
            loginButton.setOnClickListener {
                hideKeyboard(this@LoginActivity, etLoginPassword)
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                if (checkValidation(email, password)) {
                    val loginBody = LoginRegisterBody(email, password)
                    loginViewModel.login(loginBody)
                    setupLoginState()
                }
            }

            forgetTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetActivity::class.java))
            }

            tvToRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }

    }

    private fun setupLoginState() {
        loginViewModel.stateLogin.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        val intentToMain = Intent(this, MainActivity::class.java)
                        intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(this, result.error)
                    }
                }
            }
        }
    }

    private fun checkValidation(email: String, pass: String): Boolean {
        loginBinding.apply {
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etLoginEmail.error = "Masukkan Email Anda dengan benar"
                etLoginEmail.requestFocus()
            } else if (pass.isEmpty() || pass.length < 8) {
                etLoginPassword.error = "Masukkan Password Anda dengan benar"
                etLoginPassword.requestFocus()
            } else {
                return true
            }
            return false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        loginBinding.apply {
            if (isLoading) {
                loginButton.text = ""
                progressBar.visibility = View.VISIBLE
                loginButton.isEnabled = false
                tvToRegister.isEnabled = false
                forgetTextView.isEnabled = false
            } else {
                loginButton.text = getString(R.string.login)
                progressBar.visibility = View.GONE
                loginButton.isEnabled = true
                tvToRegister.isEnabled = true
                forgetTextView.isEnabled = true
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}