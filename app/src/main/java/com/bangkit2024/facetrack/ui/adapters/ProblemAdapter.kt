package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram
import com.bangkit2024.facetrack.databinding.ItemProblemBinding
import com.bangkit2024.facetrack.utils.ProblemDiffCallback

class ProblemAdapter : ListAdapter<NumberOfProblemsItemDetailProgram, ProblemAdapter.ViewHolder>(ProblemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemAdapter.ViewHolder {
        val binding = ItemProblemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProblemAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemProblemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(problem: NumberOfProblemsItemDetailProgram) {
            binding.tvJumlahProblem.text = problem.jumlah.toString()
            binding.tvNameProblem.text = problem.problem?.nama
        }
    }

}