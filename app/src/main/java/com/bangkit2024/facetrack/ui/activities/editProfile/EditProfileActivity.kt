package com.bangkit2024.facetrack.ui.activities.editProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.UserProfileBody
import com.bangkit2024.facetrack.databinding.ActivityEditProfileBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.main.MainActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.hideKeyboard
import com.bangkit2024.facetrack.utils.showToast

class EditProfileActivity : AppCompatActivity() {
    private lateinit var bindingProfile: ActivityEditProfileBinding
    private val editProfileViewModel by viewModels<EditProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var userId: Int? = null
    private var name: String? = null
    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingProfile = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(bindingProfile.root)

        if (intent != null) {
            userId = intent.getIntExtra(EXTRA_ID_USER, 0)
            name = intent.getStringExtra(EXTRA_NAME_USER) ?: ""
            phone = intent.getStringExtra(EXTRA_PHONE_USER) ?: ""
        }

        setupView()
        setupAction(userId)
    }

    private fun setupView() {
        bindingProfile.apply {
            etName.setText(name)
            etPhone.setText(phone)
        }
        setupGenderSpinner()
    }

    private fun setupAction(idUser: Int?) {
        bindingProfile.apply {
            btnInput.setOnClickListener {
                hideKeyboard(this@EditProfileActivity, etPhone)
                val nama = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val gender = spinnerSelectedToGender(spinnerGender.selectedItemPosition)

                if (checkInputValidation(nama, phone, gender)) {
                    val userProfileBody = UserProfileBody(nama, phone, gender)
                    if (idUser != null) {
                        editProfileViewModel.editProfile(idUser, userProfileBody)
                    }
                    setupStateEditProfile()
                }
            }
        }
    }

    private fun checkInputValidation(nama: String, phone: String, gender: String): Boolean {
        bindingProfile.apply {
            if (nama.isEmpty()) {
                etName.error = "Nama tidak boleh kosong"
            } else if (phone.isEmpty()) {
                etPhone.error = "Nomor telepon tidak boleh kosong"
            } else if (phone.length < 10) {
                etPhone.error = "Nomor telepon minimal 10 karakter"
            } else if (gender.isEmpty()) {
                showToast(this@EditProfileActivity, "Pilih jenis kelamin")
            } else {
                return true
            }
            return false
        }
    }

    private fun setupStateEditProfile() {
        editProfileViewModel.stateEditProfile.observe(this) { result ->
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

    private fun showLoading(isLoading: Boolean) {
        bindingProfile.apply {
            if (isLoading) {
                btnInput.text = ""
                progressBar.visibility = View.VISIBLE
                btnInput.isEnabled = false
            } else {
                btnInput.text = getString(R.string.simpan)
                progressBar.visibility = View.GONE
                btnInput.isEnabled = true
            }
        }
    }

    private fun setupGenderSpinner() {
        val spinner = bindingProfile.spinnerGender
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
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
        const val EXTRA_NAME_USER = "extra_name_user"
        const val EXTRA_PHONE_USER = "extra_phone_user"
        const val EXTRA_GENDER_USER = "extra_gender_user"
    }
}