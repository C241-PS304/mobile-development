package com.bangkit2024.facetrack.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram

class ProblemDiffCallback : DiffUtil.ItemCallback<NumberOfProblemsItemDetailProgram>() {
    override fun areItemsTheSame(oldItem: NumberOfProblemsItemDetailProgram, newItem: NumberOfProblemsItemDetailProgram): Boolean {
        return oldItem.problemNumberId == newItem.problemNumberId
    }

    override fun areContentsTheSame(oldItem: NumberOfProblemsItemDetailProgram, newItem: NumberOfProblemsItemDetailProgram): Boolean {
        return oldItem == newItem
    }
}