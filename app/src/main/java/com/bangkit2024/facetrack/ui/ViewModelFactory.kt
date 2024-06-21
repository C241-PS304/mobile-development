package com.bangkit2024.facetrack.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.data.repository.AuthRepository
import com.bangkit2024.facetrack.data.repository.UserRepository
import com.bangkit2024.facetrack.di.Injection
import com.bangkit2024.facetrack.ui.activities.addProgram.AddProgramViewModel
import com.bangkit2024.facetrack.ui.activities.detailProgram.DetailProgramViewModel
import com.bangkit2024.facetrack.ui.activities.editProfile.EditProfileViewModel
import com.bangkit2024.facetrack.ui.activities.inputProfile.InputProfileViewModel
import com.bangkit2024.facetrack.ui.activities.login.LoginViewModel
import com.bangkit2024.facetrack.ui.activities.newPassword.NewPasswordViewModel
import com.bangkit2024.facetrack.ui.activities.register.RegisterViewModel
import com.bangkit2024.facetrack.ui.activities.reset.ResetViewModel
import com.bangkit2024.facetrack.ui.activities.scanResult.ScanResultActivity
import com.bangkit2024.facetrack.ui.activities.scanResult.ScanResultViewModel
import com.bangkit2024.facetrack.ui.activities.splash.SplashViewModel
import com.bangkit2024.facetrack.ui.activities.verification.ConfirmatioOtpViewModel
import com.bangkit2024.facetrack.ui.fragments.home.HomeViewModel
import com.bangkit2024.facetrack.ui.fragments.profile.ProfileViewModel
import com.bangkit2024.facetrack.ui.fragments.program.ProgramViewModel
import com.bangkit2024.facetrack.ui.fragments.scan.ScanViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ResetViewModel::class.java) -> {
                return ResetViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ConfirmatioOtpViewModel::class.java) -> {
                return ConfirmatioOtpViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(NewPasswordViewModel::class.java) -> {
                return NewPasswordViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProgramViewModel::class.java) -> {
                return ProgramViewModel(userRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(AddProgramViewModel::class.java) -> {
                return AddProgramViewModel(authRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(DetailProgramViewModel::class.java) -> {
                return DetailProgramViewModel(authRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                return EditProfileViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(authRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                return ScanViewModel(authRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(ScanResultViewModel::class.java) -> {
                return ScanResultViewModel(authRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(InputProfileViewModel::class.java) -> {
                return InputProfileViewModel(userRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context) : ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.provideUserRepository(context)
                ).also {
                    INSTANCE = it
                }
            }
        }
    }

}