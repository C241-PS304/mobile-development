package com.bangkit2024.facetrack.ui.fragments.program

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.facetrack.data.remote.response.DataItemProgram
import com.bangkit2024.facetrack.databinding.FragmentProgramBinding
import com.bangkit2024.facetrack.ui.ViewModelFactory
import com.bangkit2024.facetrack.ui.activities.addProgram.AddProgramActivity
import com.bangkit2024.facetrack.ui.activities.detailProgram.DetailProgramActivity
import com.bangkit2024.facetrack.ui.adapters.ProgramAdapter
import com.bangkit2024.facetrack.ui.customview.DialogErrorProgram
import com.bangkit2024.facetrack.utils.Result

class ProgramFragment : Fragment() {

    private var _binding: FragmentProgramBinding? = null
    private val binding get() = _binding!!
    private val programViewModel by viewModels<ProgramViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var userToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setupRecyclerView()
        setupAction()

    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun initViewModel() {
        programViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                userToken = token
                Log.d("ProgramFragment", userToken.toString())
                programViewModel.getAllProgram(userToken.toString())
                programViewModel.checkAvailabilityProgram(userToken.toString())
                setupProgramState()
//                setupButtonState()
            }
        }
    }

    private fun setupProgramState() {
        programViewModel.stateProgram.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setupProgramData(result.data)

                        val hasEmptyProgram = result.data?.isEmpty()
                        if (hasEmptyProgram == true) {
                            binding.tvEmptyProgram.visibility = View.VISIBLE
                        } else {
                            binding.tvEmptyProgram.visibility = View.GONE
                        }

                        val hasActiveProgram = result.data?.any { it?.isActive == true }
                        if (hasActiveProgram == true) {
                            binding.btnAddProgram.visibility = View.GONE
                            binding.btnShowError.visibility = View.VISIBLE
                        } else {
                            binding.btnAddProgram.visibility = View.VISIBLE
                            binding.btnShowError.visibility = View.GONE
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            btnShowError.setOnClickListener {
                DialogErrorProgram().show(parentFragmentManager, DialogErrorProgram::class.java.simpleName)
            }

            binding.btnAddProgram.setOnClickListener {
                startActivity(Intent(requireContext(), AddProgramActivity::class.java))
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPrograms.layoutManager = layoutManager
    }

    private fun setupProgramData(programs: List<DataItemProgram?>?) {
        val adapter = ProgramAdapter()
        adapter.submitList(programs)
        binding.rvPrograms.adapter = adapter

        adapter.setOnItemClickCallback(object : ProgramAdapter.OnItemClickCallback {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(data: DataItemProgram) {
                val intentToDetailProgram = Intent(requireActivity(), DetailProgramActivity::class.java)
                intentToDetailProgram.putExtra(DetailProgramActivity.EXTRA_ID_PROGRAM, data.programId)
                startActivity(intentToDetailProgram)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                btnShowError.visibility = View.GONE
                btnAddProgram.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                btnShowError.visibility = View.VISIBLE
                btnAddProgram.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}