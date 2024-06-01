package com.bangkit2024.facetrack.ui.activities.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityLoginBinding
import com.bangkit2024.facetrack.ui.activities.main.MainActivity
import com.bangkit2024.facetrack.ui.activities.register.RegisterActivity
import com.bangkit2024.facetrack.ui.activities.reset.ResetActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

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
        loginBinding.apply {
            loginButton.setOnClickListener {
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                successDialog()
            }

            forgetTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetActivity::class.java))
            }

            regTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun successDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Login Berhasil")
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
}