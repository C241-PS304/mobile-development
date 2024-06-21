package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.NumberOfProblemsItemDetailProgram
import com.bangkit2024.facetrack.data.remote.response.SkincareItem
import com.bangkit2024.facetrack.databinding.ItemDeskripsiBinding
import com.bangkit2024.facetrack.databinding.ItemResultBinding
import com.bangkit2024.facetrack.databinding.ItemSkincareBinding
import com.bangkit2024.facetrack.utils.ProblemDiffCallback
import com.bangkit2024.facetrack.utils.SkincareDiffCallback

class SkincareAdapter : ListAdapter<SkincareItem, SkincareAdapter.ViewHolder>(
    SkincareDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkincareAdapter.ViewHolder {
        val binding = ItemSkincareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkincareAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSkincareBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SkincareItem) {
            binding.tvSkincare.text = data.nama
        }
    }
}