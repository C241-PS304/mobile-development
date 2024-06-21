package com.bangkit2024.facetrack.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram
import com.bangkit2024.facetrack.data.remote.response.SkincareItem

class SkincareDiffCallback : DiffUtil.ItemCallback<SkincareItem>() {
    override fun areItemsTheSame(oldItem: SkincareItem, newItem: SkincareItem): Boolean {
        return oldItem.skincareId == newItem.skincareId
    }

    override fun areContentsTheSame(oldItem: SkincareItem, newItem: SkincareItem): Boolean {
        return oldItem == newItem
    }
}