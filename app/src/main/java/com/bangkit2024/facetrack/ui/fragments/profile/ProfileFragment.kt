package com.bangkit2024.facetrack.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit2024.facetrack.data.remote.response.CurrentUserData
import com.bangkit2024.facetrack.databinding.FragmentProfileBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.editProfile.EditProfileActivity
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity
import com.bangkit2024.facetrack.utils.Result
import com.bangkit2024.facetrack.utils.showToast

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var userToken: String? = null
    private var userId: Int? = null

    private var userData: CurrentUserData? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun initViewModel() {
        profileViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                userToken = token
                profileViewModel.getCurrentUser(userToken.toString())
                setupStateProfile()
            }
        }
    }

    private fun setupStateProfile() {
        profileViewModel.stateProfile.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        setupProfileData(result.data)
                        userId = result.data?.id
                        userData = result.data
                        setupAction(userData)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(requireActivity(), result.error)
                    }
                }
            }
        }
    }

    private fun setupProfileData(user: CurrentUserData?) {
        binding.apply {
            tvNameProfile.text = user?.nama ?: "Nama Belum diisi"
            tvPhoneProfile.text = user?.noTelp ?: "Notelp Belum diisi"
            tvGenderProfile.text = genderString(user?.gender.toString())
        }
    }

    private fun setupAction(user: CurrentUserData?) {
        binding.apply {
            btnEdit.setOnClickListener {
                val intentToEditProfile = Intent(requireActivity(), EditProfileActivity::class.java)
                intentToEditProfile.putExtra(EditProfileActivity.EXTRA_ID_USER, userId)
                Log.d("ProfileFragment", "Nama: ${user?.nama}")
                if (user?.nama != null) {
                    intentToEditProfile.putExtra(EditProfileActivity.EXTRA_NAME_USER, user.nama)
                    intentToEditProfile.putExtra(EditProfileActivity.EXTRA_PHONE_USER, user.noTelp)
                    intentToEditProfile.putExtra(EditProfileActivity.EXTRA_GENDER_USER, user.gender)
                    Log.d("ProfileFragment", "Yey masuk")
                }
                startActivity(intentToEditProfile)
            }

            tvLogoutProfile.setOnClickListener {
                logoutDialog()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                tvLogoutProfile.isEnabled = false
                btnEdit.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                tvLogoutProfile.isEnabled = true
                btnEdit.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logoutDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Apakah ada ingin Logout ?")
            setPositiveButton("Ya") { _, _ ->
                profileViewModel.logout()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            setNegativeButton("Tidak") { _, _ -> }
            create()
            show()
        }
    }

    private fun genderString(gender: String): String {
        return when (gender) {
            "MALE" -> "Laki-Laki"
            "FEMALE" -> "Perempuan"
            else -> "Gender Belum diisi"
        }
    }

}