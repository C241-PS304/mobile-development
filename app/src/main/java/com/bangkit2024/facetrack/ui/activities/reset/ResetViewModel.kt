package com.bangkit2024.facetrack.ui.activities.reset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.ForgotPasswordBody
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import retrofit2.HttpException

class ResetViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _resetPasswordState = MutableLiveData<Result<Boolean?>>()
    val resetPasswordState: MutableLiveData<Result<Boolean?>>
        get() = _resetPasswordState


    fun resetPassword(
        forgotPasswordBody: ForgotPasswordBody
    ) = viewModelScope.launch {
        _resetPasswordState.value = Result.Loading

        try {
            val response = authRepository.forgotPassword(forgotPasswordBody)
            val isSuccessful = response.status
            _resetPasswordState.value = Result.Success(isSuccessful)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorResponse.errors
            _resetPasswordState.value = Result.Error(errorMessage.toString())
        }
    }

}