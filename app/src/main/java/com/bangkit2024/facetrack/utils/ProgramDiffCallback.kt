package com.bangkit2024.facetrack.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit2024.facetrack.data.remote.response.DataItemProgram

class ProgramDiffCallback : DiffUtil.ItemCallback<DataItemProgram>() {
    override fun areItemsTheSame(oldItem: DataItemProgram, newItem: DataItemProgram): Boolean {
        return oldItem.programId == newItem.programId
    }

    override fun areContentsTheSame(oldItem: DataItemProgram, newItem: DataItemProgram): Boolean {
        return oldItem == newItem
    }
}