package com.bangkit2024.facetrack.ui.activities.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.LoginRegisterBody
import com.bangkit2024.facetrack.data.remote.response.DataRegister
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel()  {

    private val _stateRegister = MutableLiveData<Result<DataRegister?>>()
    val stateRegister: LiveData<Result<DataRegister?>>
        get() = _stateRegister

    fun register(
        loginRegisterBody: LoginRegisterBody
    ) = viewModelScope.launch {
        _stateRegister.value = Result.Loading

        try {
            val response = authRepository.register(loginRegisterBody)
            val dataRegister = response.data

            val token = response.data?.accessToken
            authRepository.setUserToken(token.toString())
            authRepository.updateRegisterUser(false)

            _stateRegister.value = Result.Success(dataRegister)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateRegister.value = Result.Error(errorMessage.toString())
        }
    }
}