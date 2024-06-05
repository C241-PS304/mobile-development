package com.bangkit2024.facetrack.ui.fragments.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.databinding.FragmentIntroBinding
import com.bumptech.glide.Glide

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
                setUpData(
                    R.drawable.intro_1,
                    getString(R.string.title_intro_1),
                    getString(R.string.subtitle_intro_1)
                )
            }
            "2" -> {
                setUpData(
                    R.drawable.intro_2,
                    getString(R.string.title_intro_2),
                    getString(R.string.subtitle_intro_2)
                )
            }
            "3" -> {
                setUpData(
                    R.drawable.intro_3,
                    getString(R.string.title_intro_3),
                    getString(R.string.subtitle_intro_3)
                )
            }
        }
    }

    private fun setUpData(resId: Int, txtTitle: String, txtSubtitle: String) {
        Glide.with(requireActivity())
            .load(resId)
            .fitCenter()
            .into(binding.ivIntro)
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