package com.bangkit2024.facetrack.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit2024.facetrack.data.remote.response.ScanItem

class ScanDiffCallback : DiffUtil.ItemCallback<ScanItem>() {
    override fun areItemsTheSame(oldItem: ScanItem, newItem: ScanItem): Boolean {
        return oldItem.scanId == newItem.scanId
    }

    override fun areContentsTheSame(oldItem: ScanItem, newItem: ScanItem): Boolean {
        return oldItem == newItem
    }
}