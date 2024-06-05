package com.bangkit2024.facetrack.ui.activities.scanResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.DataItemProblem
import com.bangkit2024.facetrack.data.remote.response.DetailProgramResponse
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.remote.response.ScanResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class ScanResultViewModel(private val authRepository: AuthRepository,private val userRepository: UserRepository): ViewModel() {
    private val _allproblem = MutableLiveData<ArrayList<DataItemProblem?>>()
    val problembyId: LiveData<ArrayList<DataItemProblem?>> get()= _allproblem

    private val _detailprogram = MutableLiveData<DetailProgramResponse>()
    val detailprogram: LiveData<DetailProgramResponse> = _detailprogram

    private val _stateScanResult = MutableLiveData<ScanResponse>()
    val stateScanResult: LiveData<ScanResponse>
        get() = _stateScanResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    private fun setErrorText(errorMessage: String?) {
        _errorText.value = errorMessage
    }

    private val _idProgram = MutableLiveData<String>()
    val idProgram : LiveData<String>
        get() = _idProgram

    init {
        getidProgramActive()
    }

    fun addScan(multipartBody: MultipartBody.Part, programId: Int, problemId: String, jumlah: String){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val token = authRepository.getUserToken()
                val response = userRepository.addScan(token, multipartBody, programId, problemId, jumlah)
                _stateScanResult.value = response
                Log.e("ScanResultViewModel", "$response")
                _isLoading.value = false
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                setErrorText(errorMessage)
                Log.e("ScanResultViewModel", "$errorMessage")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun getDetailProgram(id: Int){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val token = authRepository.getUserToken()
                val response = userRepository.getDetailProgram(token, id)
                _detailprogram.value = response
            } catch (e: Exception) {
                setErrorText("Gagal Mendapatkan Data")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProblem(id: IntArray){
        viewModelScope.launch {
            try {
                val token = authRepository.getUserToken()
                val response = userRepository.getAllProblems(token)
                response.data?.let { list ->
                    val item = ArrayList(list.filter { it?.problemId!! in id })
                    _allproblem.postValue(item)
                }
            } catch (e: Exception) {
                setErrorText("Gagal Mendapatkan Data")
            }
        }
    }

    private fun getidProgramActive() = viewModelScope.launch {
        _idProgram.value = userRepository.getProgramId()
    }
}