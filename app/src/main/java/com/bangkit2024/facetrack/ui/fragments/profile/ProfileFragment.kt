package com.bangkit2024.facetrack.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.databinding.FragmentProfileBinding
import com.bangkit2024.facetrack.ui.activities.editProfile.EditProfileActivity
import com.bangkit2024.facetrack.ui.activities.inputProfile.InputProfileActivity
import com.bangkit2024.facetrack.ui.activities.login.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel : ProfileViewModel

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

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        // Code here
        binding.btnEdit.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.tvLogoutProfile.setOnClickListener {
            logoutDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logoutDialog(){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Apakah ada ingin Logout ?")
            setPositiveButton("Ya") { _, _ ->
                profileViewModel.signOut()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
            setNegativeButton("Tidak"){_, _ ->
            }
            create()
            show()
        }
    }
}