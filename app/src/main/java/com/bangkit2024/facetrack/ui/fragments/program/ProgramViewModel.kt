package com.bangkit2024.facetrack.ui.fragments.program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgramViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is program Fragment"
    }
    val text: LiveData<String> = _text
}