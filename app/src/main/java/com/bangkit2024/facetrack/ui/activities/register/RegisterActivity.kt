package com.bangkit2024.facetrack.ui.activities.register

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityRegisterBinding
import com.bangkit2024.facetrack.ui.activities.inputProfile.InputProfileActivity
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registrasi"

        setupView()
        setupAction()

    }

    private fun setupView() {
        registerBinding.apply {
            emailEditTextLayout.setEditText(etLoginEmail)
            passwordEditTextLayout.setEditText(etLoginPassword)
        }
    }

    private fun setupAction(){
        registerBinding.apply {
            registerButton.setOnClickListener {
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                startActivity(Intent(this@RegisterActivity, InputProfileActivity::class.java))
            }

            loginTextView.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}