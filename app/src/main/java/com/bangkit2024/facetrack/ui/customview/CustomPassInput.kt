package com.bangkit2024.facetrack.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.bangkit2024.facetrack.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomPassInput(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                error = if (it.length < 8) {
                    context.getString(R.string.password_validation)
                } else {
                    null
                }
            }
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
    fun setEditText(editText: TextInputEditText) {
        editText.addTextChangedListener(textWatcher)
    }
}