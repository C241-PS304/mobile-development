package com.bangkit2024.facetrack.ui.activities.detailProgram

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.facetrack.data.remote.response.DataDetailProgram
import com.bangkit2024.facetrack.data.remote.response.ScanItem
import com.bangkit2024.facetrack.data.remote.response.SkincareItemProgram
import com.bangkit2024.facetrack.databinding.ActivityDetailProgramBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.result.ResultActivity
import com.bangkit2024.facetrack.ui.adapters.ScanAdapter
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.showToast
import com.bangkit2024.facetrack.utils.ubahFormatTanggal

@RequiresApi(Build.VERSION_CODES.O)
class DetailProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProgramBinding
    private val detailProgramViewModel by viewModels<DetailProgramViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var userToken: String? = null
    private var idProgram: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            idProgram = intent.getIntExtra(EXTRA_ID_PROGRAM, 0)
        }

        setupAction()
        setupRecyclerView()

        idProgram?.let {
            initViewModel(it)
        }

    }

    private fun setupAction() {
        binding.apply {
            btnDone.setOnClickListener {
                successDialog(
                    "Yakin ingin menyelesaikan program ini?",
                    "Pastikan bahwa penyakit anda sudah mulai mereda"
                )
            }
        }
    }

    private fun initViewModel(idProgram: Int) {
        detailProgramViewModel.token.observe(this) { token ->
            if (token != null) {
                userToken = token
                detailProgramViewModel.getDetailProgram(token.toString(), idProgram)
                setupStateDetailProgram()
            }
        }
    }

    private fun setupStateDetailProgram() {
        detailProgramViewModel.stateDetailProgram.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        if (result.data?.isActive == false) {
                            binding.btnDone.visibility = View.GONE
                        }
                        setupDataProgram(result.data)
                        setupDataScan(result.data?.scan)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showToast(this, result.error)
                    }
                }
            }
        }
    }

    private fun setupDataScan(data: List<ScanItem?>?) {
        val adapter = ScanAdapter()
        adapter.submitList(data)
        binding.rvScans.adapter = adapter

        adapter.setOnItemClickCallback(object : ScanAdapter.OnItemClickCallback {
            override fun onClick(data: ScanItem) {
                val intentToResult = Intent(this@DetailProgramActivity, ResultActivity::class.java)
                intentToResult.putExtra(ResultActivity.EXTRA_IMAGE, data.gambar)
                intentToResult.putExtra(ResultActivity.EXTRA_PROBLEMS, data)
                startActivity(intentToResult)
            }
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvScans.layoutManager = layoutManager
    }

    private fun setupDataProgram(data: DataDetailProgram?) {
        binding.apply {
            tvNamaProgram.text = data?.namaProgram
            tvProduk.text = concateNameSkincare(data?.skincare)
            tvTanggal.text = ubahFormatTanggal(data?.createdAt.toString())
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                tvTitleNamaProgram.visibility = View.GONE
                tvTitleProduk.visibility = View.GONE
                tvTitleTanggal.visibility = View.GONE
                btnDone.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                tvTitleNamaProgram.visibility = View.VISIBLE
                tvTitleProduk.visibility = View.VISIBLE
                tvTitleTanggal.visibility = View.VISIBLE
                btnDone.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun concateNameSkincare(skincares: List<SkincareItemProgram?>?): String? {
        return skincares?.joinToString(",") { it?.nama.toString() }
    }

    private fun successDialog(title: String, message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                detailProgramViewModel.finishProgram(userToken.toString(), idProgram as Int)
                detailProgramViewModel.stateFinishProgram.observe(this@DetailProgramActivity) { result ->
                    when(result) {
                        is Result.Loading -> {}
                        is Result.Success -> { finish() }
                        is Result.Error -> { showToast(this@DetailProgramActivity, result.error) }
                    }
                }
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }
    }
    companion object {
        const val EXTRA_ID_PROGRAM = "extra_id_program"
    }
}