package com.bangkit2024.facetrack.ui.activities.scanResult

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.facetrack.databinding.ActivityScanResultBinding
import com.bangkit2024.facetrack.model.Disease
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.detailProgram.DetailProgramActivity
import com.bangkit2024.facetrack.ui.adapters.DeskripsiScanResultAdapter
import com.bangkit2024.facetrack.ui.adapters.DiseaseAdapter
import com.bangkit2024.facetrack.ui.adapters.ProblemAdapter
import com.bangkit2024.facetrack.ui.adapters.SaranScanResultAdapter
import com.bangkit2024.facetrack.utils.ImageUtils.loadBitmapWithOrientation
import com.bangkit2024.facetrack.utils.ImageUtils.uriToFile
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.showToast
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    private var idProgramActive: Int? = 0
    private var imageUri: Uri? = null
    private lateinit var bitmap: Bitmap
    private lateinit var deskripsiScanResultAdapter: DeskripsiScanResultAdapter
    private lateinit var saranScanResultAdapter: SaranScanResultAdapter
    private lateinit var diseaseAdapter: DiseaseAdapter

    private var userToken: String? = null
    private var listproblems: ArrayList<Int>? = null
    private var image: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanResultBinding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(scanResultBinding.root)

        scanResultBinding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (intent != null) {
            listproblems = intent.getIntegerArrayListExtra("listidProblem")
            image = intent.getStringExtra("image")
        }
        scanResultBinding.tvTanggalMulai.text = getCurrentDateFormatted()
//
        Log.d("ScanResult", listproblems.toString())

        imageUri = Uri.parse(image)
        if (imageUri != null) {
            val bitmap = loadBitmapWithOrientation(this, imageUri)
            Glide.with(this)
                .load(bitmap)
                .into(scanResultBinding.ivHasilScan)
        }

        viewModel.token.observe(this) { token ->
            userToken = token

            if (listproblems != null) {
                viewModel.getProblem(userToken.toString(), listproblems!!.toIntArray())
            }

            viewModel.idProgram.observe(this) { id ->
                idProgramActive = id.toString().toInt()
                viewModel.getDetailProgram(
                    userToken.toString(),
                    idProgramActive!!
                )
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.problembyId.observe(this) { it ->
            it.let {
                val diseaseCountList: List<Disease>
                val disease = mutableListOf<String>()
                for (i in it) {
                    disease.add(i?.nama.toString())
                }
                val countMap = countOccurrences(listproblems!!.toTypedArray())
                val uniqueDisease = countMap.keys.toList().joinToString(",")
                val countNumbers = countMap.values.toList().joinToString(",")
                diseaseCountList = countMap.entries.mapIndexed { index, entry ->
                    Disease(disease[index % disease.size], entry.value)
                }
                Log.d("ScanResultActivity", "Disease: $disease")
                Log.d("ScanResultActivity", "Countmap: $countMap")
                Log.d("ScanResultActivity", "$diseaseCountList")
                Log.e("ScanResultActivity", "$imageUri")
                imageUri?.let { uri ->
                    val imageFile = uriToFile(uri, this)
                    val requestImageFile = imageFile.asRequestBody("image/png".toMediaType())
                    val multipartBody = MultipartBody.Part.createFormData(
                        "file",
                        imageFile.name,
                        requestImageFile
                    )

                    val requestIdProgram = idProgramActive.toString().toRequestBody("text/plain".toMediaType())
                    val requestIdProblem = uniqueDisease.toRequestBody("text/plain".toMediaType())
                    val requestCountNumbers = countNumbers.toRequestBody("text/plain".toMediaType())

                    Log.e("Multipart", "$multipartBody")
                    Log.e("IdProgram", "$idProgramActive")
                    Log.e("UniqueDisease", uniqueDisease)
                    Log.e("CountNumbers", countNumbers)

                    scanResultBinding.btnSave.setOnClickListener {
                        viewModel.addScan(
                            userToken.toString(),
                            multipartBody,
                            requestIdProgram,
                            requestIdProblem,
                            requestCountNumbers
                        )
                        viewModel.stateScanResult.observe(this) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }

                                is Result.Success -> {
                                    showLoading(false)
                                    val intentToDetailProgram =
                                        Intent(this, DetailProgramActivity::class.java)
                                        intentToDetailProgram.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                        intentToDetailProgram.putExtra(DetailProgramActivity.EXTRA_ID_PROGRAM, idProgramActive
                                    )
                                    startActivity(intentToDetailProgram)
                                    finish()
                                }

                                is Result.Error -> {
                                    showLoading(false)
                                    showToast(this, result.error)
                                }
                            }
                        }
                    }
                }


                scanResultBinding.rvProblems.layoutManager = LinearLayoutManager(this)
                diseaseAdapter = DiseaseAdapter(diseaseCountList)
                scanResultBinding.rvProblems.adapter = diseaseAdapter

                scanResultBinding.rvDeskripsi.layoutManager = LinearLayoutManager(this)
                deskripsiScanResultAdapter = DeskripsiScanResultAdapter(it)
                scanResultBinding.rvDeskripsi.adapter = deskripsiScanResultAdapter

                scanResultBinding.rvSaran.layoutManager = LinearLayoutManager(this)
                saranScanResultAdapter = SaranScanResultAdapter(it)
                scanResultBinding.rvSaran.adapter = saranScanResultAdapter
            }
        }

        viewModel.detailprogram.observe(this) { program ->
            if (program != null) {
                val lastScan = program.data?.scan?.last()
                if (lastScan != null) {
                    scanResultBinding.cardviewbefore.visibility = View.VISIBLE
                    scanResultBinding.tvCompare.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(lastScan.gambar)
                        .into(scanResultBinding.ivHasilScanBefore)

                    scanResultBinding.rvProblemsBefore.layoutManager = LinearLayoutManager(this)
                    val diseaseBeforeAdapter = ProblemAdapter()
                    diseaseBeforeAdapter.submitList(lastScan.numberOfProblems)
                    scanResultBinding.rvProblemsBefore.adapter = diseaseBeforeAdapter
                } else {
                    scanResultBinding.cardviewbefore.visibility = View.GONE
                    scanResultBinding.tvCompare.visibility = View.GONE
                }
            }
        }
    }

    private fun countOccurrences(array: Array<Int>): Map<Int, Int> {
        val countMap = mutableMapOf<Int, Int>()
        for (number in array) {
            countMap[number] = countMap.getOrDefault(number, 0) + 1
        }
        return countMap
    }

    private fun getCurrentDateFormatted(): String {
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