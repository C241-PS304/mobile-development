package com.bangkit2024.facetrack.ui.activities.addProgram

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.request.NewProgramBody
import com.bangkit2024.facetrack.databinding.ActivityAddProgramBinding
import com.bangkit2024.facetrack.model.Skincare
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.hideKeyboard
import com.bangkit2024.facetrack.utils.showToast

class AddProgramActivity : AppCompatActivity() {

    private lateinit var addProgramBinding: ActivityAddProgramBinding
    private val addProgramViewModel by viewModels<AddProgramViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var userToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addProgramBinding = ActivityAddProgramBinding.inflate(layoutInflater)
        setContentView(addProgramBinding.root)

        initViewModel()
        setupAction()
    }

    private fun initViewModel() {
        addProgramViewModel.token.observe(this) { token ->
            if (token != null) {
                userToken = token
            }
        }
    }

    private fun setupAction() {
        addProgramBinding.apply {
            inputButton.setOnClickListener {
                hideKeyboard(this@AddProgramActivity, etProduk)
                val namaProgram = etProgram.text.toString().trim()
                val skincareStr = etProduk.text.toString().trim()

                if (checkValidation(namaProgram, skincareStr)) {
                    val listSkincare = setupSkincaresString(skincareStr)
                    val newProgramBody = NewProgramBody(
                        namaProgram,
                        listSkincare
                    )
                    addProgramViewModel.addProgram(userToken.toString(), newProgramBody)
                    setupStateAddProgram()
                }
            }
        }
    }

    private fun checkValidation(namaProgram: String, skincareStr: String): Boolean {
        addProgramBinding.apply {
            if (namaProgram.isEmpty()) {
                etProgram.error = "Masukkan program dengan benar"
            } else if (skincareStr.isEmpty()) {
                etProduk.error = "Masukkan skincare dengan benar"
            } else {
                return true
            }
            return false
        }
    }

    private fun setupStateAddProgram() {
        addProgramViewModel.stateNewProgram.observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
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
        addProgramBinding.apply {
            if (isLoading) {
                inputButton.text = ""
                progressBar.visibility = View.VISIBLE
                inputButton.isEnabled = false
            } else {
                inputButton.text = getString(R.string.tambah)
                progressBar.visibility = View.GONE
                inputButton.isEnabled = true
            }
        }
    }

    private fun setupSkincaresString(str: String): List<Skincare> {
        val cleanedStr = str.replace(" ", "").split(",")
        return cleanedStr.map { Skincare(it) }
    }

}