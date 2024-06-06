package com.bangkit2024.facetrack.ui.activities.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {

    private lateinit var bindingPreview: ActivityPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingPreview = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(bindingPreview.root)

        setupReceiveIntent()
    }

    private fun setupReceiveIntent() {
        val imageFromScan = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)
        val imageUri = imageFromScan?.toUri()

        bindingPreview.ivPreview.setImageURI(imageUri)
    }

    companion object {
        const val EXTRA_CAMERAX_IMAGE = "camerax_image"
    }
}