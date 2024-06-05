package com.bangkit2024.facetrack.ui.fragments.scan

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit2024.facetrack.databinding.FragmentScanBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.addProgram.AddProgramActivity
import com.bangkit2024.facetrack.ui.activities.scanCamera.ScanCameraActivity
import com.bangkit2024.facetrack.utils.showToast

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ScanViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                showToast(requireActivity(), "Camera Permission Granted")
            } else {
                showToast(requireActivity(), "Camera Permission Denied")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.checkAvailProgram()
        viewModel.saveIdProgramActive()

        binding.apply {
            viewModel.program.observe(viewLifecycleOwner){ program ->
                if (program.status != true){
                    tvNoProgram.visibility = View.GONE
                    btnBuatProgram.visibility = View.GONE
                    btnScan.visibility = View.VISIBLE
                    tvKetentuan.visibility = View.VISIBLE
                    tvPoinKetentuan.visibility = View.VISIBLE
                    ivScan.visibility = View.VISIBLE
                }else{
                    tvNoProgram.visibility = View.VISIBLE
                    btnBuatProgram.visibility = View.VISIBLE
                    btnScan.visibility = View.GONE
                    tvKetentuan.visibility = View.GONE
                    tvPoinKetentuan.visibility = View.GONE
                    ivScan.visibility = View.GONE
                }
            }

            if (!allPermissionGranted()) {
                requestPermissionLauncher.launch(CAMERAX_PERMISSION)
            }

            btnScan.setOnClickListener { startCameraX() }
            btnBuatProgram.setOnClickListener {
                val intent = Intent(requireActivity(), AddProgramActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            CAMERAX_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startCameraX() {
        val intentToCamera = Intent(requireActivity(), ScanCameraActivity::class.java)
        startActivity(intentToCamera)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val CAMERAX_PERMISSION = android.Manifest.permission.CAMERA
    }
}