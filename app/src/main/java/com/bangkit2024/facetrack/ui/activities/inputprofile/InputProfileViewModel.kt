package com.bangkit2024.facetrack.ui.activities.inputProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.UserProfileBody
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.UserRepository
import kotlinx.coroutines.launch
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import retrofit2.HttpException

class InputProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stateInputProfile = MutableLiveData<Result<Boolean?>>()
    val stateInputProfile: LiveData<Result<Boolean?>>
        get() = _stateInputProfile

    fun saveUserProfile(
        id: Int,
        userProfileBody: UserProfileBody
    ) = viewModelScope.launch {
        _stateInputProfile.value = Result.Loading

        try {
            val response = userRepository.updateUser(id, userProfileBody)
            val dataInputProfile = response.status
            _stateInputProfile.value = Result.Success(dataInputProfile)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorBody = errorResponse.errors
            _stateInputProfile.value = Result.Error(errorBody.toString())
        }

    }
}