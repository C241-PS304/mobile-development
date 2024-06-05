package com.bangkit2024.facetrack.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.response.CurrentUserData
import com.bangkit2024.facetrack.databinding.FragmentHomeBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.editProfile.EditProfileActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.showToast
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var userToken: String? = null
    private var idUser: Int? = null

    private var pressedTime = 0L

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                requireActivity().finish()
            } else {
                showToast(requireActivity(), "Tekan lagi untuk keluar")
            }

            pressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Code here
        initViewModel()
        setupAction()
    }

    private fun initViewModel() {
        homeViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                userToken = token
                homeViewModel.getCurrentUser(token)
                setupStateCurrentUser()
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            btnUbahData.setOnClickListener {
                val intentToEditProfile = Intent(requireActivity(), EditProfileActivity::class.java)
                intentToEditProfile.putExtra(EditProfileActivity.EXTRA_ID_USER, idUser)
                startActivity(intentToEditProfile)
            }
        }
    }

    private fun setupStateCurrentUser() {
        homeViewModel.stateHome.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setupDataUser(result.data)
                        idUser = result.data?.id
                        getLastScanByProgramActive(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showToast(requireActivity(), result.error)
                    }
                }
            }
        }
    }

    private fun setupDataUser(data: CurrentUserData?) {
        if (data?.nama.isNullOrEmpty()) {
            binding.tvUser.visibility = View.GONE
            binding.cvDataUser.visibility = View.VISIBLE
        } else {
            binding.tvUser.visibility = View.VISIBLE
            binding.tvUser.text = data?.nama
            binding.cvDataUser.visibility = View.GONE

            val constrainLayout = binding.home
            val constrainSet = ConstraintSet()
            constrainSet.clone(constrainLayout)

            constrainSet.connect(
                R.id.tv2,
                ConstraintSet.TOP,
                R.id.tv_user,
                ConstraintSet.BOTTOM,
                48
            )

            constrainSet.applyTo(constrainLayout)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                tv1.visibility = View.GONE
                tv2.visibility = View.GONE
                cvLastScan.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                tv1.visibility = View.VISIBLE
                tv2.visibility = View.VISIBLE
                cvLastScan.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun getLastScanByProgramActive(data: CurrentUserData?) {
        val activeProgram = data?.program?.find { it?.active == true }
        val lastScan = activeProgram?.scan?.lastOrNull()

        if (activeProgram != null && lastScan != null) {
            Glide.with(requireActivity())
                .load(lastScan.gambar)
                .centerCrop()
                .into(binding.ivLastScan)

            binding.tvProgramLastScan.text = activeProgram.namaProgram

            val listProblem =
                lastScan.numberOfProblems?.joinToString(", ") {
                    idProblemToNameProblem(it?.problemNumberId ?: 0)
                }
            binding.tvProblemLastScan.text = listProblem ?: "No problems found"
        }
    }

    private fun idProblemToNameProblem(number: Int): String {
        return when(number) {
            1 -> "Jerawat"
            2 -> "Mata Panda"
            3 -> "Kerutan"
            else -> ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}