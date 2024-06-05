package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.databinding.ItemProblemBinding
import com.bangkit2024.facetrack.model.Disease

class DiseaseAdapter (private val listproblem: List<Disease>) : RecyclerView.Adapter<DiseaseAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProblemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: Disease) {
            with(binding) {
                tvNameProblem.text = disease.name
                tvJumlahProblem.text = disease.count.toString()
            }
        }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseAdapter.ViewHolder {
        val binding = ItemProblemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiseaseAdapter.ViewHolder, position: Int) {
        val item = listproblem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listproblem.size
    }

}