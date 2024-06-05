package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram
import com.bangkit2024.facetrack.databinding.ItemSaranBinding
import com.bangkit2024.facetrack.utils.ProblemDiffCallback

class SaranAdapter : ListAdapter<NumberOfProblemsItemDetailProgram, SaranAdapter.ViewHolder>(
    ProblemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaranAdapter.ViewHolder {
        val binding = ItemSaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaranAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSaranBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NumberOfProblemsItemDetailProgram) {
            binding.tvProblem.text = data.problem?.nama
            binding.tvSaran.text = data.problem?.deskrpsi
        }
    }
}