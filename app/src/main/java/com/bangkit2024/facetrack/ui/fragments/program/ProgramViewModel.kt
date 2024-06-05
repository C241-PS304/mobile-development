package com.bangkit2024.facetrack.ui.fragments.program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.DataItemProgram
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProgramViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _stateProgram = MutableLiveData<Result<List<DataItemProgram?>?>>()
    val stateProgram: LiveData<Result<List<DataItemProgram?>?>>
        get() = _stateProgram

    private val _stateAvailabilityProgram = MutableLiveData<Result<Boolean?>>()
    val stateAvailabilityProgram: LiveData<Result<Boolean?>>
        get() = _stateAvailabilityProgram

    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getUserToken()
    }

    fun getAllProgram(
        token: String
    ) = viewModelScope.launch {
        _stateProgram.value = Result.Loading

        try {
            val responseProgram = userRepository.getAllPrograms("Bearer $token")
            val dataProgram = responseProgram.data
            _stateProgram.value = Result.Success(dataProgram)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateProgram.value = Result.Error(errorMessage.toString())
        }
    }

    fun checkAvailabilityProgram(
        token: String
    ) = viewModelScope.launch {
        _stateAvailabilityProgram.value = Result.Loading

        try {
            val responseAvailability = userRepository.checkAvailability("Bearer $token")
            val dataAvailability = responseAvailability.status
            _stateAvailabilityProgram.value = Result.Success(dataAvailability)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateAvailabilityProgram.value = Result.Error(errorMessage.toString())
        }
    }

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }

}