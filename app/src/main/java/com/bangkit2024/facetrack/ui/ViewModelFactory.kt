package com.bangkit2024.facetrack.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.ui.activities.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val authRepository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
//            return RegisterViewModel(authRepository) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(
            authRepository: AuthRepository
        ) : ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    authRepository
                ).also {
                    INSTANCE = it
                }
            }
        }
    }

}