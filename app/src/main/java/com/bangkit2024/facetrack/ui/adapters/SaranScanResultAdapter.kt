package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.DataItemProblem
import com.bangkit2024.facetrack.databinding.ItemSaranBinding

class SaranScanResultAdapter(private val listproblem: ArrayList<DataItemProblem?>) : RecyclerView.Adapter<SaranScanResultAdapter.ViewHolder>() {

    class ViewHolder(var binding : ItemSaranBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : DataItemProblem){
            binding.tvProblem.text = item.nama.toString()
            binding.tvSaran.text = item.saran.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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