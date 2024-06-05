package com.bangkit2024.facetrack.ui.activities.newPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.ChangePasswordBody
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewPasswordViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _stateNewPassword = MutableLiveData<Result<Boolean?>>()
    val stateNewPassword: MutableLiveData<Result<Boolean?>>
        get() = _stateNewPassword

    fun newPassword(
        changePasswordBody: ChangePasswordBody,
    ) = viewModelScope.launch {
        _stateNewPassword.value = Result.Loading

        try {
            val response = authRepository.changePassword(changePasswordBody)
            val result = response.status
            _stateNewPassword.value = Result.Success(result)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorResponse.errors
            _stateNewPassword.value = Result.Error(errorMessage.toString())
        }
    }

}