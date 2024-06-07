package com.bangkit2024.facetrack.ui.fragments.program

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit2024.facetrack.databinding.FragmentProgramBinding
import com.bangkit2024.facetrack.ui.activities.addProgram.AddProgramActivity
import com.bangkit2024.facetrack.ui.activities.detailProgram.DetailProgramActivity
import com.bangkit2024.facetrack.ui.customview.DialogErrorProgram

class ProgramFragment : Fragment() {

    private var _binding: FragmentProgramBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* # Button State
        * If there are still active programs, hide add button and show error dialog
        * else show add button and hide error message dialog
        * */

        // Example if there are still active program (temporary)
        binding.btnShowError.setOnClickListener {
            // Show error dialog
            DialogErrorProgram().show(parentFragmentManager, DialogErrorProgram::class.java.simpleName)
        }

        binding.btnAddProgram.setOnClickListener {
            startActivity(Intent(requireContext(), AddProgramActivity::class.java))
        }

        binding.exampleItemProgram.root.setOnClickListener {
            startActivity(Intent(requireContext(), DetailProgramActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}