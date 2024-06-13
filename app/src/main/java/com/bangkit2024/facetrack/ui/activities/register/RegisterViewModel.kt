package com.bangkit2024.facetrack.ui.activities.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.bangkit2024.facetrack.utils.Result

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableLiveData<Result<String?>>()
    val registerState: LiveData<Result<String?>> get() = _registerState

//    fun register(
//        email: String,
//        password: String
//    ) = viewModelScope.launch {
//        _registerState.value = Result.Loading
//
//        try {
//            val response = authRepository.register(email, password)
//            val messageSucess = response.
//
//        } catch (e: Exception) {
//
//        }
//    }
}