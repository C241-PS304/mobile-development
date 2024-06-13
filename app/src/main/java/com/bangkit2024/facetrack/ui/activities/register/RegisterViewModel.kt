package com.bangkit2024.facetrack.ui.activities.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel: ViewModel()  {

    private val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    private val auth = FirebaseAuth.getInstance()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _successText = MutableLiveData<String?>()
    val successText: LiveData<String?> = _successText

    private val _failureText = MutableLiveData<String?>()
    val failureText: LiveData<String?> = _failureText
    fun setSuccessMessage(message: String?) {
        _successText.value = message
    }
    fun setFailureMessage(message: String?) {
        _failureText.value = message
    }
    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun registerFirebase(email: String?, pass: String?) {
        auth.createUserWithEmailAndPassword(email!!, pass!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(auth.currentUser)
                setSuccessMessage("Registrasi Berhasil")
            } else {
                setFailureMessage("Email Sudah Terdaftar")
            }
        }
    }
}