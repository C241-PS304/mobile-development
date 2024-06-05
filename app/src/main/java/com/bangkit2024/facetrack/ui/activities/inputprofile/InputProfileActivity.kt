package com.bangkit2024.facetrack.ui.activities.inputProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.UserProfileBody
import com.bangkit2024.facetrack.databinding.ActivityInputProfileBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.main.MainActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.hideKeyboard
import com.bangkit2024.facetrack.utils.showToast

class InputProfileActivity : AppCompatActivity() {

    private lateinit var inputProfileBinding: ActivityInputProfileBinding
    private val inputProfileViewModel by viewModels<InputProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var pressedTime = 0L
    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                showToast(this@InputProfileActivity, "Tekan lagi untuk keluar")
            }

            pressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inputProfileBinding = ActivityInputProfileBinding.inflate(layoutInflater)
        setContentView(inputProfileBinding.root)

        onBackPressedDispatcher.addCallback(this, backCallback)

        val idUser = intent.getIntExtra(EXTRA_ID_USER, 0)
        setupAction(idUser)
        setupGenderSpinner()
    }

    private fun setupAction(idUser: Int) {
        inputProfileBinding.apply {
            btnInput.setOnClickListener {
                hideKeyboard(this@InputProfileActivity, etPhone)
                val nama = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val gender = spinnerSelectedToGender(spinnerGender.selectedItemPosition)

                if (checkInputValidation(nama, phone, gender)) {
                    val userProfileBody = UserProfileBody(nama, phone, gender)
                    inputProfileViewModel.saveUserProfile(idUser, userProfileBody)
                    setupStateInputProfile()
                }
            }
        }
    }

    private fun setupGenderSpinner() {
        val spinner = inputProfileBinding.spinnerGender
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupStateInputProfile() {
        inputProfileViewModel.stateInputProfile.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
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

    private fun checkInputValidation(nama: String, phone: String, gender: String): Boolean {
        inputProfileBinding.apply {
            if (nama.isEmpty()) {
                etName.error = "Nama tidak boleh kosong"
            } else if (phone.isEmpty()) {
                etPhone.error = "Nomor telepon tidak boleh kosong"
            } else if (phone.length < 10) {
                etPhone.error = "Nomor telepon minimal 10 karakter"
            } else if (gender.isEmpty()) {
                showToast(this@InputProfileActivity, "Pilih jenis kelamin")
            } else {
                return true
            }
            return false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        inputProfileBinding.apply {
            if (isLoading) {
                btnInput.text = ""
                progressBar.visibility = View.VISIBLE
                btnInput.isEnabled = false
            } else {
                btnInput.text = getString(R.string.login)
                progressBar.visibility = View.GONE
                btnInput.isEnabled = true
            }
        }
    }

    private fun spinnerSelectedToGender(id: Int): String {
        return when(id) {
            0 -> "MALE"
            1 -> "FEMALE"
            else -> ""
        }
    }

    companion object {
        const val EXTRA_ID_USER = "extra_id_user"
    }
}