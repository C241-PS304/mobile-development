package com.bangkit2024.facetrack.ui.fragments.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.ProgramAvailabilityResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import kotlinx.coroutines.launch

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

    fun checkAvailProgram() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val token = authRepository.getUserToken()
                val response = userRepository.checkProgram(token)
                _program.value = response
            } catch (e: Exception) {
                setErrorText("Mohon Periksa Koneksi Internet Anda")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveIdProgramActive(){
        viewModelScope.launch {
            try {
                val token = authRepository.getUserToken()
                val response = userRepository.getAllPrograms(token)
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
    }
}