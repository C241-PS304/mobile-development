package com.bangkit2024.facetrack.ui.activities.scanResult

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.facetrack.databinding.ActivityScanResultBinding
import com.bangkit2024.facetrack.model.Disease
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.adapters.DeskripsiScanResultAdapter
import com.bangkit2024.facetrack.ui.adapters.DiseaseAdapter
import com.bangkit2024.facetrack.ui.adapters.SaranScanResultAdapter
import com.bangkit2024.facetrack.utils.ImageUtils.uriToFile
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ScanResultActivity : AppCompatActivity() {
    private lateinit var scanResultBinding: ActivityScanResultBinding
    private val viewModel by viewModels<ScanResultViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var idProgramActive :Int? = 0
    private var imageUri: Uri? = null
    private lateinit var bitmap: Bitmap
    private lateinit var deskripsiScanResultAdapter: DeskripsiScanResultAdapter
    private lateinit var saranScanResultAdapter: SaranScanResultAdapter
    private lateinit var diseaseAdapter: DiseaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanResultBinding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(scanResultBinding.root)

        val image = intent.getStringExtra("image")
        val listproblems = intent.getIntegerArrayListExtra("listidProblem")
        scanResultBinding.tvTanggalMulai.text = getCurrentDateFormatted()

        imageUri = Uri.parse(image)
        if (imageUri != null) {
            bitmap = loadBitmapWithOrientation(imageUri)
            Glide.with(this)
                .load(bitmap)
                .into(scanResultBinding.ivHasilScan)
        }

        if (listproblems != null) {
            viewModel.getProblem(listproblems.toIntArray())
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.problembyId.observe(this){ it ->
            it.let {
                for (i in it){
                    val disease = listOf(i?.nama.toString())
                    val countMap = countOccurrences(listproblems!!.toTypedArray())
                    val uniqueDisease = countMap.keys.toList().joinToString(",")
                    val countNumbers = countMap.values.toList().joinToString(",")
                    val diseaseCountList = countMap.entries.mapIndexed { index, entry ->
                        Disease(disease[index % disease.size], entry.value)
                    }
                    Log.e("ScanResultActivity", "$imageUri")
                    imageUri?.let { uri ->
                        val imageFile = uriToFile(uri, this)
                        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                        val multipartBody = MultipartBody.Part.createFormData(
                            "photo",
                            imageFile.name,
                            requestImageFile
                        )

                        Log.e("Multipart", "$multipartBody")
                        Log.e("IdProgram", "$idProgramActive")
                        Log.e("UniqueDisease", uniqueDisease)
                        Log.e("CountNumbers", countNumbers)

                        scanResultBinding.btnSave.setOnClickListener {
                            viewModel.addScan(multipartBody, idProgramActive!!, uniqueDisease, countNumbers)
                        }
                    }

                    scanResultBinding.rvProblems.layoutManager = LinearLayoutManager(this)
                    diseaseAdapter = DiseaseAdapter(diseaseCountList)
                    scanResultBinding.rvProblems.adapter = diseaseAdapter
                }
                scanResultBinding.rvDeskripsi.layoutManager = LinearLayoutManager(this)
                deskripsiScanResultAdapter = DeskripsiScanResultAdapter(it)
                scanResultBinding.rvDeskripsi.adapter = deskripsiScanResultAdapter

                scanResultBinding.rvSaran.layoutManager = LinearLayoutManager(this)
                saranScanResultAdapter = SaranScanResultAdapter(it)
                scanResultBinding.rvSaran.adapter = saranScanResultAdapter
            }
        }
        viewModel.idProgram.observe(this){ id ->
            idProgramActive = id.toString().toInt()
            viewModel.getDetailProgram(idProgramActive!!)
        }
        viewModel.detailprogram.observe(this){ program ->
            if (program != null){
                program.data!!.let {
                    if (it.scan!!.isNotEmpty()){
                        scanResultBinding.cardviewbefore.visibility = View.VISIBLE
                        scanResultBinding.tvCompare.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun loadBitmapWithOrientation(imageUri: Uri?): Bitmap {
        val originalBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val inputStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
        var orientation = ExifInterface.ORIENTATION_UNDEFINED

        if (inputStream != null) {
            try {
                val exifInterface = ExifInterface(inputStream)
                orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
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

    private fun countOccurrences(array: Array<Int>): Map<Int, Int> {
        val countMap = mutableMapOf<Int, Int>()
        for (number in array) {
            countMap[number] = countMap.getOrDefault(number, 0) + 1
        }
        return countMap
    }

    fun getCurrentDateFormatted(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
            current.format(formatter)
        } else {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date = Date()
            dateFormat.format(date)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        scanResultBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}