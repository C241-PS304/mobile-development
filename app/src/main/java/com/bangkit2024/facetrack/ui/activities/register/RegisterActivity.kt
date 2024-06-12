package com.bangkit2024.facetrack.ui.activities.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.databinding.ActivityRegisterBinding
import com.bangkit2024.facetrack.ui.activities.inputProfile.InputProfileActivity
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity
import com.bangkit2024.facetrack.ui.activities.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setupView()
        setupAction()

    }

    private fun setupView() {
        registerBinding.apply {
            emailEditTextLayout.setEditText(etRegistEmail)
            passwordEditTextLayout.setEditText(etRegistPassword)
        }
    }

    private fun setupAction(){

        registerViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        registerViewModel.successText.observe(this) {
            if (it != null) {
                registerViewModel.setSuccessMessage(null)
                successDialog(it)
            }
        }
        registerViewModel.failureText.observe(this){
            if (it != null) {
                registerViewModel.setFailureMessage(null)
                failedDialog(it)
            }
        }
        registerBinding.apply {
            registerButton.setOnClickListener {
                val email = etRegistEmail.text.toString()
                val password = etRegistPassword.text.toString()
                val cpass = etRegistCpass.text.toString()

                if(checkValidation(email,password,cpass)){
                    registerViewModel.registerFirebase(email,password)
                }
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

    private fun showLoading(isLoading: Boolean) {
        registerBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkValidation(email: String, pass: String, cpass: String): Boolean {
        registerBinding.apply {
            if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etRegistEmail.error = "Masukkan Email Anda dengan benar"
                etRegistEmail.requestFocus()
            } else if (pass.isEmpty()||pass.length < 8) {
                etRegistPassword.error = "Masukkan Password Anda dengan benar"
                etRegistPassword.requestFocus()
            } else if (cpass.isEmpty()||cpass.length < 8) {
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

    private fun successDialog(message: String){
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
    private fun failedDialog(message: String){
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setPositiveButton("Kembali") { _, _ -> }
            create()
            show()
        }
    }
}