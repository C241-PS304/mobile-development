package com.bangkit2024.facetrack.ui.activities.editProfile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.facetrack.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var bindingProfile: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingProfile = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(bindingProfile.root)
    }
}