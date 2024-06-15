package com.bangkit2024.facetrack.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {
    private val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth = FirebaseAuth.getInstance()

    fun signOut() {
        auth.signOut()
        userLoggedMutableLiveData.postValue(true)
    }
}