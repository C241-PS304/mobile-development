package com.bangkit2024.facetrack.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.CurrentUserData
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stateProfile = MutableLiveData<Result<CurrentUserData?>>()
    val stateProfile: MutableLiveData<Result<CurrentUserData?>> = _stateProfile

    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getUserToken()
    }

    fun getCurrentUser(
        token: String
    ) = viewModelScope.launch {
        _stateProfile.value = Result.Loading

        try {
            val response = authRepository.getCurrentUser("Bearer $token")
            val data = response.data
            _stateProfile.value = Result.Success(data)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorResponse.errors
            _stateProfile.value = Result.Error(errorMessage.toString())
        }
    }

    fun logout() = viewModelScope.launch {
        authRepository.logout()
    }

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }

}