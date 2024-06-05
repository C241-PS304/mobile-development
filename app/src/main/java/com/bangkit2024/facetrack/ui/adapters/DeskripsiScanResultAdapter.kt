package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.DataItemProblem
import com.bangkit2024.facetrack.databinding.ItemDeskripsiBinding

class DeskripsiScanResultAdapter(private val listproblem: ArrayList<DataItemProblem?>) : RecyclerView.Adapter<DeskripsiScanResultAdapter.ViewHolder>() {

    class ViewHolder(var binding : ItemDeskripsiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : DataItemProblem){
            binding.tvProblem.text = item.nama.toString()
            binding.tvDescription.text = item.deskrpsi.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeskripsiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listproblem[position]
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return listproblem.size
    }
}