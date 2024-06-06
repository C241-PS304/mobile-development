package com.bangkit2024.facetrack.ui.fragments.scan

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.databinding.FragmentScanBinding
import com.bangkit2024.facetrack.ui.activities.scanCamera.ScanCameraActivity
import com.bangkit2024.facetrack.utils.showToast

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

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

        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(CAMERAX_PERMISSION)
        }

        binding.btnScan.setOnClickListener { startCameraX() }

        // Code here
        binding.tvNoProgram.visibility = View.GONE
        binding.btnBuatProgram.visibility = View.GONE
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

    companion object {
        private const val CAMERAX_PERMISSION = android.Manifest.permission.CAMERA
    }
}