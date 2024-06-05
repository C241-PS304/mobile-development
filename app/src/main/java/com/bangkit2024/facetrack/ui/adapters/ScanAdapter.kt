package com.bangkit2024.facetrack.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.data.remote.response.ScanItem
import com.bangkit2024.facetrack.databinding.ItemScanBinding
import com.bangkit2024.facetrack.utils.ScanDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

@RequiresApi(Build.VERSION_CODES.O)
class ScanAdapter : ListAdapter<ScanItem, ScanAdapter.ScanViewHolder>(ScanDiffCallback()) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanAdapter.ScanViewHolder {
        val binding = ItemScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanAdapter.ScanViewHolder, position: Int) {
        holder.bind(getItem(position), position == itemCount - 1)
    }

    inner class ScanViewHolder(private val binding: ItemScanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ScanItem, isLastItem: Boolean) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.gambar)
                    .centerCrop()
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(ivHasilScan)

                val problemAdapter = ProblemAdapter()
                rvProblems.layoutManager = LinearLayoutManager(itemView.context)
                problemAdapter.submitList(data.numberOfProblems)
                rvProblems.adapter = problemAdapter

                itemView.setOnClickListener {
                    onItemClickCallback?.onClick(data)
                }

                rvProblems.setOnClickListener {
                    onItemClickCallback?.onClick(data)
                }

                ivArrowDown.visibility = if (isLastItem) View.GONE else View.VISIBLE
            }
        }
    }

    interface OnItemClickCallback {
        fun onClick(data: ScanItem)
    }

}