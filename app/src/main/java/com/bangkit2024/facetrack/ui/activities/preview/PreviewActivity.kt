package com.bangkit2024.facetrack.ui.activities.preview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.facetrack.databinding.ActivityPreviewBinding
import com.bangkit2024.facetrack.ui.activities.scanResult.ScanResultActivity
import com.bangkit2024.facetrack.utils.Recognition
import com.bangkit2024.facetrack.utils.YoloTfliteDetector
import com.bangkit2024.facetrack.utils.showToast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream

class PreviewActivity : AppCompatActivity() {

    private lateinit var bindingPreview: ActivityPreviewBinding
    private lateinit var yoloTfliteDetector: YoloTfliteDetector
    private lateinit var bitmap: Bitmap
    private lateinit var strokePaint: Paint
    private lateinit var boxPaint: Paint
    private lateinit var textPaint: Paint
    private var imageUri: Uri? = null
    private var listidProblem: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPreview = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(bindingPreview.root)

        setupYoloTfliteDetector()
        setupReceiveIntent()
        setUpBoundingBox()

        bindingPreview.btnHasil.visibility = View.GONE
        bindingPreview.btnDetect.setOnClickListener {
            lifecycleScope.launch {
                val recognitions = yoloTfliteDetector.detect(bitmap)
                Log.d(TAG, recognitions.toString())
                processDetectionResults(recognitions)
                bindingPreview.btnDetect.visibility = View.GONE
                bindingPreview.btnHasil.visibility = View.VISIBLE
            }
        }

        bindingPreview.btnHasil.setOnClickListener {
            val image = intent.getStringExtra(EXTRA_CAMERAX_IMAGE).toString()
            val intent = Intent(this, ScanResultActivity::class.java)
            intent.putExtra("image", image)
            intent.putExtra("listidProblem", listidProblem)
            startActivity(intent)
            finish()
        }
    }

    private fun setupReceiveIntent() {
        imageUri = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)?.toUri()

        if (imageUri != null) {
            bitmap = loadBitmapWithOrientation(imageUri)

            bindingPreview.ivPreview.scaleX = -1f
            Glide.with(this)
                .load(bitmap)
                .into(bindingPreview.ivPreview)
        } else {
            showToast(this, "Image Kosong")
        }
    }

    private fun setupYoloTfliteDetector() {
        yoloTfliteDetector = YoloTfliteDetector()
        yoloTfliteDetector.modelFile = "model_skin_track.tflite"
        yoloTfliteDetector.initialModel(this)
    }

    @SuppressLint("DefaultLocale")
    private fun processDetectionResults(recognitions: List<Recognition>) {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        val width = mutableBitmap.width

        for (recognition in recognitions) {
            if (recognition.confidence > 0.6) {
                val location = recognition.location
                val flippedLocation = RectF(
                    width - location.right,
                    location.top,
                    width - location.left,
                    location.bottom
                )
                listidProblem.add(recognition.labelId+1)

                chooseBoundingBox(recognition.labelId)
                canvas.drawRect(flippedLocation, boxPaint)
                canvas.drawRect(flippedLocation, strokePaint)

                canvas.save()
                // Apply the transformation to flip the text back
                canvas.scale(-1f, 1f, flippedLocation.left, flippedLocation.top)

                // Calculate the position for the text
                val textX = -flippedLocation.right
                val textY = flippedLocation.top
                canvas.drawText(
                    "${recognition.labelName}:${
                        String.format(
                            "%.2f",
                            recognition.confidence
                        )
                    }", textX, textY, textPaint
                )
                canvas.restore()
            }
        }

        Glide.with(this)
            .asBitmap()
            .load(mutableBitmap)
            .transition(BitmapTransitionOptions.withCrossFade())
            .into(bindingPreview.ivPreview)

    }

    private fun loadBitmapWithOrientation(imageUri: Uri?): Bitmap {
        val originalBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

        val inputStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
        var orientation = ExifInterface.ORIENTATION_UNDEFINED

        if (inputStream != null) {
            try {
                val exifInterface = ExifInterface(inputStream)
                orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream.close()
            }
        }
        return rotateBitmap(originalBitmap, orientation)
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun setUpBoundingBox() {
        strokePaint = Paint()
        strokePaint.strokeWidth = 8F
        strokePaint.style = Paint.Style.STROKE

        boxPaint = Paint()
        boxPaint.style = Paint.Style.FILL

        textPaint = Paint()
        textPaint.textSize = 50F
        textPaint.style = Paint.Style.FILL
    }

    private fun chooseBoundingBox(label: Int) {
        when (label) {
            0 -> {
                strokePaint.color = Color.BLUE
                boxPaint.color = Color.argb(50, 0, 0, 255)
                textPaint.color = Color.BLUE
            }

            1 -> {
                strokePaint.color = Color.GREEN
                boxPaint.color = Color.argb(50, 0, 255, 0)
                textPaint.color = Color.GREEN
            }

            2 -> {
                strokePaint.color = Color.RED
                boxPaint.color = Color.argb(50, 255, 0, 0)
                textPaint.color = Color.RED
            }

            3 -> {
                strokePaint.color = Color.RED
                boxPaint.color = Color.argb(50, 255, 0, 0)
                textPaint.color = Color.RED
            }

            4 -> {
                strokePaint.color = Color.RED
                boxPaint.color = Color.argb(50, 255, 0, 0)
                textPaint.color = Color.RED
            }

            5 -> {
                strokePaint.color = Color.RED
                boxPaint.color = Color.argb(50, 255, 0, 0)
                textPaint.color = Color.RED
            }
        }
    }

    companion object {
        private const val TAG = "PreviewActivity"
        const val EXTRA_CAMERAX_IMAGE = "camerax_image"
    }
}