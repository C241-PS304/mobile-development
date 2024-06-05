package com.bangkit2024.facetrack.ui.activities.result

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram
import com.bangkit2024.facetrack.data.remote.response.ScanItem
import com.bangkit2024.facetrack.databinding.ActivityResultBinding
import com.bangkit2024.facetrack.ui.adapters.DeskripsiAdapter
import com.bangkit2024.facetrack.ui.adapters.ProblemAdapter
import com.bangkit2024.facetrack.ui.adapters.SaranAdapter

class ResultActivity : AppCompatActivity() {
    private lateinit var resultBinding: ActivityResultBinding

    private var image: String? = null
    private var dataDetailProgram: ScanItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)

        if (intent != null) {
            image = intent.getStringExtra(EXTRA_IMAGE)
            dataDetailProgram = if (Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra(EXTRA_PROBLEMS, ScanItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra<ScanItem>(EXTRA_PROBLEMS)
            }
        }

        val listProblem = dataDetailProgram?.numberOfProblems
        setupRecyclerView()
        setupDataResult(listProblem)

    }

    private fun setupRecyclerView() {
        resultBinding.rvProblems.layoutManager = LinearLayoutManager(this)
        resultBinding.rvDeskripsi.layoutManager = LinearLayoutManager(this)
        resultBinding.rvSaran.layoutManager = LinearLayoutManager(this)
    }

    private fun setupDataResult(data: List<NumberOfProblemsItemDetailProgram?>?) {
        val adapter = ProblemAdapter()
        adapter.submitList(data)
        resultBinding.rvProblems.adapter = adapter

        val deskripsiAdapter = DeskripsiAdapter()
        deskripsiAdapter.submitList(data)
        resultBinding.rvDeskripsi.adapter = deskripsiAdapter

        val saranAdapter = SaranAdapter()
        saranAdapter.submitList(data)
        resultBinding.rvSaran.adapter = saranAdapter
    }

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_PROBLEMS = "extra_problems"
    }
}