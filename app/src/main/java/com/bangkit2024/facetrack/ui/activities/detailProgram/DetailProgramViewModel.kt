package com.bangkit2024.facetrack.ui.activities.detailProgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.DataDetailProgram
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailProgramViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stateDetailProgram = MutableLiveData<Result<DataDetailProgram?>>()
    val stateDetailProgram: LiveData<Result<DataDetailProgram?>>
        get() = _stateDetailProgram

    private val _stateFinishProgram = MutableLiveData<Result<Boolean?>>()
    val stateFinishProgram: LiveData<Result<Boolean?>>
        get() = _stateFinishProgram

    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getUserToken()
    }

    fun getDetailProgram(
        token: String,
        id: Int
    ) = viewModelScope.launch {
        _stateDetailProgram.value = Result.Loading

        try {
            val response = userRepository.getDetailProgram("Bearer $token", id)
            val result = response.data
            _stateDetailProgram.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateDetailProgram.value = Result.Error(errorMessage.toString())
        }
    }

    fun finishProgram(
        token: String,
        id: Int
    ) = viewModelScope.launch {
        _stateFinishProgram.value = Result.Loading

        try {
            val response = userRepository.updateStatusProgram("Bearer $token", id)
            val result = response.status
            _stateFinishProgram.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateFinishProgram.value = Result.Error(errorMessage.toString())
        }
    }

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }

}