package com.bangkit2024.facetrack.ui.fragments.program

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.facetrack.databinding.FragmentProgramBinding

class ProgramFragment : Fragment() {

    private var _binding: FragmentProgramBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val programViewModel =
            ViewModelProvider(this)[ProgramViewModel::class.java]

        _binding = FragmentProgramBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textProgram
        programViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
}