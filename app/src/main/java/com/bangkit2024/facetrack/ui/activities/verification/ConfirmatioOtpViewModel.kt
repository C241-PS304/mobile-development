package com.bangkit2024.facetrack.ui.activities.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.ConfirmOtpBody
import com.bangkit2024.facetrack.data.remote.request.ForgotPasswordBody
import com.bangkit2024.facetrack.data.remote.response.ConfirmOTPResponse
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ConfirmatioOtpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _stateVerification = MutableLiveData<Result<Boolean?>>()
    val stateVerification: LiveData<Result<Boolean?>>
        get() = _stateVerification

    private val _resendOtpState = MutableLiveData<Result<Boolean?>>()
    val resetPasswordState: MutableLiveData<Result<Boolean?>>
        get() = _resendOtpState

    private val _timeLeft = MutableLiveData<Long>()
    val timeLeft: LiveData<Long>
        get() = _timeLeft

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean>
        get() = _isButtonEnabled

    private var countdownJob: Job? = null

    init {
        startCountdownTimer()
    }

    fun verification(
        confirmOtpBody: ConfirmOtpBody
    ) = viewModelScope.launch {
        _stateVerification.value = Result.Loading

        try {
            val response = authRepository.confirmationOtp(confirmOtpBody)
            val result = response.status
            _stateVerification.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(jsonInString, ConfirmOTPResponse::class.java)
            val errorMessage = errorResponse.message
            _stateVerification.value = Result.Error(errorMessage.toString())
        }
    }

    fun resendOtp(
        forgotPasswordBody: ForgotPasswordBody
    ) = viewModelScope.launch {
        _resendOtpState.value = Result.Loading

        try {
            val response = authRepository.forgotPassword(forgotPasswordBody)
            val isSuccessful = response.status
            _resendOtpState.value = Result.Success(isSuccessful)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorResponse.errors
            _resendOtpState.value = Result.Error(errorMessage.toString())
        }
    }

    fun startCountdownTimer(startSeconds: Long = 30) {
        _isButtonEnabled.value = false
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (i in startSeconds downTo 0) {
                _timeLeft.value = i
                delay(1000)
            }
            _isButtonEnabled.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }

}