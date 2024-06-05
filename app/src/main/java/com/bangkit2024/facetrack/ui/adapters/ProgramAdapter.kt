package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.data.remote.response.DataItemProgram
import com.bangkit2024.facetrack.databinding.ItemProgramBinding
import com.bangkit2024.facetrack.utils.ProgramDiffCallback

class ProgramAdapter : ListAdapter<DataItemProgram, ProgramAdapter.ListViewHolder>(ProgramDiffCallback()) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(private val binding: ItemProgramBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemProgram) {
            with(binding) {
                tvTitleProgram.text = data.namaProgram
                tvStatus.text = statusToText(data.isActive)
                tvStatus.setTextColor(statusToColor(data.isActive))
                tvStatus.setBackgroundResource(statusToColor(data.isActive))

                itemView.setOnClickListener {
                    onItemClickCallback?.onClick(data)
                }
            }
        }

        private fun statusToText(isActive: Boolean?): String {
            return if (isActive == true) "Aktif" else "Selesai"
        }

        private fun statusToColor(isActive: Boolean?): Int {
            return if (isActive == true) {
                R.drawable.bg_status_yellow
            } else {
                R.drawable.bg_status_green
            }
        }
    }

    interface OnItemClickCallback {
        fun onClick(data: DataItemProgram)
    }

}