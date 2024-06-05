package com.bangkit2024.facetrack.ui.activities.newPassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.ChangePasswordBody
import com.bangkit2024.facetrack.databinding.ActivityNewPasswordBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.showToast

class NewPasswordActivity : AppCompatActivity() {

    private lateinit var newPasswordBinding: ActivityNewPasswordBinding
    private val newPasswordViewModel by viewModels<NewPasswordViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newPasswordBinding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(newPasswordBinding.root)

        if (intent != null) {
            email = intent.getStringExtra(EXTRA_EMAIL).toString()
        }

        setupAction(email)
    }

    private fun setupAction(email: String) {
        newPasswordBinding.apply {
            btnSimpan.setOnClickListener {
                val password = etPassword.text.toString().trim()
                val cpass = etCpass.text.toString().trim()

                if (checkValidation(password, cpass)) {
                    val changePasswordBody = ChangePasswordBody(email, password)
                    newPasswordViewModel.newPassword(changePasswordBody)
                    setupStateNewPassword()
                }
            }

            // Back Button
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setupStateNewPassword() {
        newPasswordViewModel.stateNewPassword.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        successDialog("Yeay password mu sudah berhasil diubah")
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showToast(this, result.error)
                    }
                }
            }
        }
    }

    private fun checkValidation(password: String, cpass: String): Boolean {
        newPasswordBinding.apply {
            if (password.isEmpty() || password.length < 8) {
                etPassword.error = "Password minimal 8 karakter"
            } else if (password != cpass) {
                etCpass.error = "Masukkan konfirmasi password dengan benar"
            } else {
                return true
            }
            return false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        newPasswordBinding.apply {
            if (isLoading) {
                btnSimpan.text = ""
                progressBar.visibility = View.VISIBLE
                btnSimpan.isEnabled = false
            } else {
                btnSimpan.text = getString(R.string.simpan)
                progressBar.visibility = View.GONE
                btnSimpan.isEnabled = true
            }
        }
    }

    private fun successDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setPositiveButton("Lanjutkan") { _, _ ->
                val intentToMain = Intent(this@NewPasswordActivity, LoginActivity::class.java)
                intentToMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentToMain)
                finish()
            }
            setCancelable(false)
            create()
            show()
        }
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}