package com.bangkit2024.facetrack.ui.activities.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.facetrack.data.remote.request.UserProfileBody
import com.bangkit2024.facetrack.data.remote.response.ErrorResponse
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.bangkit2024.facetrack.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EditProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stateEditProfile = MutableLiveData<Result<Boolean?>>()
    val stateEditProfile: LiveData<Result<Boolean?>>
        get() = _stateEditProfile

    fun editProfile(
        id: Int,
        userProfileBody: UserProfileBody
    ) = viewModelScope.launch {
        _stateEditProfile.value = Result.Loading

        try {
            val response = userRepository.updateUser(id, userProfileBody)
            val data = response.status
            _stateEditProfile.value = Result.Success(data)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            _stateEditProfile.value = Result.Error(errorMessage.toString())
        }
    }

}