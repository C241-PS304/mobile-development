package com.bangkit2024.facetrack.ui.activities.preview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.ActivityPreviewBinding
import com.bangkit2024.facetrack.ui.activities.scanResult.ScanResultActivity

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

        if (imageUri != null) {
            bindingPreview.ivPreview.setImageURI(imageUri)
            bindingPreview.ivPreview.scaleX = -1f
        }

        bindingPreview.btnDetect.setOnClickListener {
            startActivity(Intent(this, ScanResultActivity::class.java))
        }
    }

    companion object {
        const val EXTRA_CAMERAX_IMAGE = "camerax_image"
    }
}