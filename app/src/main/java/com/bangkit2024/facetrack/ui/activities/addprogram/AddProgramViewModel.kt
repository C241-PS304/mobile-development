package com.bangkit2024.facetrack.ui.activities.addProgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.NewProgramBody
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import kotlinx.coroutines.launch
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import retrofit2.HttpException

class AddProgramViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stateNewProgram = MutableLiveData<Result<Boolean?>>()
    val stateNewProgram: LiveData<Result<Boolean?>>
        get() = _stateNewProgram

    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getUserToken()
    }

    fun addProgram(
        token: String,
        newProgramBody: NewProgramBody
    ) = viewModelScope.launch {
        _stateNewProgram.value = Result.Loading

        try {
            val response = userRepository.addProgram("Bearer $token", newProgramBody)
            val result = response.status
            _stateNewProgram.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateNewProgram.value = Result.Error(errorMessage.toString())
        }
    }

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }

}