package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram
import com.bangkit2024.facetrack.databinding.ItemDeskripsiBinding
import com.bangkit2024.facetrack.utils.ProblemDiffCallback

class DeskripsiAdapter : ListAdapter<NumberOfProblemsItemDetailProgram, DeskripsiAdapter.ViewHolder>(
    ProblemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeskripsiAdapter.ViewHolder {
        val binding = ItemDeskripsiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeskripsiAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDeskripsiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NumberOfProblemsItemDetailProgram) {
            binding.tvProblem.text = data.problem?.nama
            binding.tvDescription.text = data.problem?.deskrpsi
        }
    }
}