package com.bangkit2024.facetrack.ui.activities.scanResult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.response.DataItemProblem
import com.bangkit2024.facetrack.data.remote.response.DataScan
import com.bangkit2024.facetrack.data.remote.response.DetailProgramResponse
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.remote.response.ScanResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import com.bangkit2024.facetrack.utils.Result

class ScanResultViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _allproblem = MutableLiveData<ArrayList<DataItemProblem?>>()
    val problembyId: LiveData<ArrayList<DataItemProblem?>> get()= _allproblem

    private val _detailprogram = MutableLiveData<DetailProgramResponse>()
    val detailprogram: LiveData<DetailProgramResponse> = _detailprogram

    private val _stateScanResult = MutableLiveData<Result<DataScan?>>()
    val stateScanResult: LiveData<Result<DataScan?>>
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

    private val _token = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        getidProgramActive()
        getUserToken()
    }

    fun addScan(
        token: String,
        multipartBody: MultipartBody.Part,
        programId: RequestBody,
        problemId: RequestBody,
        jumlah: RequestBody
    ) = viewModelScope.launch {
        _stateScanResult.value = Result.Loading

        try {
            val response = userRepository.addScan("Bearer $token", multipartBody, programId, problemId, jumlah)
            val result = response.data
            _stateScanResult.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateScanResult.value = Result.Error(errorMessage.toString())
        }
    }

    fun getDetailProgram(
        token: String,
        id: Int
    ){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.getDetailProgram("Bearer $token", id)
                _detailprogram.value = response
            } catch (e: Exception) {
                setErrorText("Gagal Mendapatkan Data")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProblem(
        token: String,
        id: IntArray)
    {
        viewModelScope.launch {
            try {
                val response = userRepository.getAllProblems("Bearer $token")
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

    private fun getUserToken() = viewModelScope.launch {
        _token.value = authRepository.getUserToken()
    }
}