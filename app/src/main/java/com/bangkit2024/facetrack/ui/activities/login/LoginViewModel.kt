package com.bangkit2024.facetrack.ui.activities.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.LoginRegisterBody
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _stateLogin = MutableLiveData<Result<Boolean?>>()
    val stateLogin: LiveData<Result<Boolean?>>
        get() = _stateLogin

    fun login(
        loginRegisterBody: LoginRegisterBody
    ) = viewModelScope.launch {
        _stateLogin.value = Result.Loading

        try {
            val response = authRepository.login(loginRegisterBody)
            val isSuccess = response.status

            val token = response.data?.accessToken
             authRepository.setUserToken(token.toString())
            _stateLogin.value = Result.Success(isSuccess)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateLogin.value = Result.Error(errorMessage.toString())
        }
    }

}