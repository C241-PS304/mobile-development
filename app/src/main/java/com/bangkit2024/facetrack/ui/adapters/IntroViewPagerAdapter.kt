package com.bangkit2024.facetrack.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit2024.facetrack.ui.fragments.intro.IntroFragment

class IntroViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val dataFragments = mutableListOf(
        IntroFragment.newInstance("1"),
        IntroFragment.newInstance("2"),
        IntroFragment.newInstance("3"),
    )
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = dataFragments[position]
}