package com.bangkit2024.facetrack.ui.activities.scanCamera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bangkit2024.facetrack.databinding.ActivityScanCameraBinding
import com.bangkit2024.facetrack.ui.activities.preview.PreviewActivity
import com.bangkit2024.facetrack.utils.createCustomTempFile
import com.bangkit2024.facetrack.utils.showToast

class ScanCameraActivity : AppCompatActivity() {

    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var imageCapture: ImageCapture? = null

    private lateinit var scanCameraBinding: ActivityScanCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanCameraBinding = ActivityScanCameraBinding.inflate(layoutInflater)
        setContentView(scanCameraBinding.root)

        startCamera()

        scanCameraBinding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        scanCameraBinding.ivCapture.setOnClickListener {
            takePhoto()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(scanCameraBinding.previewCamera.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                showToast(this, "Failed to show camera")
                Log.e(TAG, e.message.toString())
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createCustomTempFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    showToast(this@ScanCameraActivity, "Berhasil mengambil gambar")

                    val savedUri = outputFileResults.savedUri.toString()

                    // Intent ke Preview
                    val intentToPreview = Intent(this@ScanCameraActivity, PreviewActivity::class.java)
                    intentToPreview.putExtra(PreviewActivity.EXTRA_CAMERAX_IMAGE, savedUri)
                    startActivity(intentToPreview)
                }

                override fun onError(exception: ImageCaptureException) {
                    showToast(this@ScanCameraActivity, "Gagal mengambil gambar")
                    Log.e(TAG, exception.message.toString())
                }

            }
        )
    }

    companion object {
        private const val TAG = "ScanCameraActivity"
    }

}