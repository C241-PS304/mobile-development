package com.bangkit2024.facetrack.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.CurrentUserData
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _stateHome = MutableLiveData<Result<CurrentUserData?>>()
    val stateHome: LiveData<Result<CurrentUserData?>> = _stateHome



    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getUserToken()
    }

    fun getCurrentUser(
        token: String
    ) = viewModelScope.launch {
        _stateHome.value = Result.Loading

        try {
            val response = authRepository.getCurrentUser("Bearer $token")
            val data = response.data
            _stateHome.value = Result.Success(data)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateHome.value = Result.Error(errorMessage.toString())
        }
    }

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }
}