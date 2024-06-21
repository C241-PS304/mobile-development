package com.bangkit2024.facetrack.ui.fragments.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.remote.response.ProgramAvailabilityResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ScanViewModel(private val authRepository: AuthRepository,private val userRepository: UserRepository) : ViewModel() {
    private val _program = MutableLiveData<ProgramAvailabilityResponse>()
    val program: LiveData<ProgramAvailabilityResponse> = _program

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    private fun setErrorText(errorMessage: String?) {
        _errorText.value = errorMessage
    }

    private val _stateCheckAvailProgram = MutableLiveData<Result<Boolean?>>()
    val stateCheckAvailProgram: LiveData<Result<Boolean?>> = _stateCheckAvailProgram

    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getUserToken()
    }

    fun checkAvailProgram(
        token: String
    ) = viewModelScope.launch {
        _stateCheckAvailProgram.value = Result.Loading

        try {
            val response = userRepository.checkAvailability("Bearer $token")
            val result = response.status
            _stateCheckAvailProgram.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateCheckAvailProgram.value = Result.Error(errorMessage.toString())
        }
    }

    fun saveIdProgramActive(
        token: String
    ) = viewModelScope.launch {
        try {
            val response = userRepository.getAllPrograms("Bearer $token")
            response.data?.let { data ->
                for (item in data){
                    if (item?.isActive == true){
                        val id = item.programId.toString()
                        userRepository.saveProgramId(id)
                    }
                }
            }
        } catch (e: Exception) {
            setErrorText("Gagal Mendapatkan Id Program")
        }
    }

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }
}