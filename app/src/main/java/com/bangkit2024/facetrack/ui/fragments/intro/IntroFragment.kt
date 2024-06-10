package com.bangkit2024.facetrack.ui.fragments.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    private var param: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(param) {
            "1" -> {
                setUpData(R.drawable.ic_launcher_background, "Intro 1", "Lorem ipsum dolor sit amet consectetur. Pellentesque eu integer")
            }
            "2" -> {
                setUpData(R.drawable.ic_launcher_background, "Intro 2", "Lorem ipsum dolor sit amet consectetur. Pellentesque eu integer")
            }
            "3" -> {
                setUpData(R.drawable.ic_launcher_background, "Intro 3", "Lorem ipsum dolor sit amet consectetur. Pellentesque eu integer")
            }
        }
    }

    private fun setUpData(resId: Int, txtTitle: String, txtSubtitle: String) {
        binding.ivIntro.setBackgroundResource(resId)
        binding.tvTitleIntro.text = txtTitle
        binding.tvSubtitleIntro.text = txtSubtitle
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_PARAM = "param"

        @JvmStatic
        fun newInstance(param: String) =
            IntroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}