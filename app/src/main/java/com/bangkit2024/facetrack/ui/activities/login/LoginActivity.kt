package com.bangkit2024.facetrack.ui.activities.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.databinding.ActivityLoginBinding
import com.bangkit2024.facetrack.ui.activities.inputProfile.InputProfileActivity
import com.bangkit2024.facetrack.ui.activities.main.MainActivity
import com.bangkit2024.facetrack.ui.activities.register.RegisterActivity
import com.bangkit2024.facetrack.ui.activities.reset.ResetActivity
import com.bangkit2024.facetrack.utils.showToast
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
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
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setupView()
        setupAction()

    }

    private fun setupView() {
        loginBinding.apply {
            emailEditTextLayout.setEditText(etLoginEmail)
            passwordEditTextLayout.setEditText(etLoginPassword)
        }
    }

    private fun setupAction(){
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        loginViewModel.successText.observe(this) {
            if (it != null) {
                loginViewModel.setSuccessMessage(null)
                successDialog(it)
            }
        }
        loginViewModel.failureText.observe(this){
            if (it != null) {
                loginViewModel.setFailureMessage(null)
                failedDialog(it)
            }
        }
        loginBinding.apply {
            loginButton.setOnClickListener {
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                if(checkValidation(email,password)){
                    loginViewModel.loginFirebase(email,password)
                }
            }

            forgetTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetActivity::class.java))
            }

            regTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }


    }

    private fun checkValidation(email: String, pass: String): Boolean {
        loginBinding.apply {
            if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etLoginEmail.error = "Masukkan Email Anda dengan benar"
                etLoginEmail.requestFocus()
            } else if (pass.isEmpty()||pass.length < 8) {
                etLoginPassword.error = "Masukkan Password Anda dengan benar"
                etLoginPassword.requestFocus()
            } else {
                return true
            }
            return false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        loginBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun successDialog(message: String){
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setPositiveButton("OK") { _, _ ->
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }
    private fun failedDialog(message: String){
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setPositiveButton("Kembali") { _, _ -> }
            create()
            show()
        }
    }
}