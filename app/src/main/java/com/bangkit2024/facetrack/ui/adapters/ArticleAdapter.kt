package com.bangkit2024.facetrack.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.databinding.ItemArticleBinding
import com.bangkit2024.facetrack.model.Article

class ArticleAdapter (private val article: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Article){
            binding.ivArtikel.setImageResource(item.articleImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = article[position]
        holder.bind(article)
        holder.binding.ivArtikel.setImageResource(article.articleImage)
    }
    override fun getItemCount(): Int {
        return article.size
    }
}