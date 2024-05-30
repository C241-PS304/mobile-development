package com.bangkit2024.facetrack.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.facetrack.R
import com.bangkit2024.facetrack.adapter.ArticleAdapter
import com.bangkit2024.facetrack.databinding.FragmentHomeBinding
import com.bangkit2024.facetrack.model.Article

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleList:ArrayList<Article>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showArticle()
        // Code here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showArticle(){
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        articleList = ArrayList()
        addDatatoList()
        articleAdapter = ArticleAdapter(articleList)
        binding.rvArticle.adapter = articleAdapter
    }

    private fun addDatatoList(){
        articleList.add(Article(R.drawable.imagedummy))
        articleList.add(Article(R.drawable.imagedummy))
        articleList.add(Article(R.drawable.imagedummy))
        articleList.add(Article(R.drawable.imagedummy))
        articleList.add(Article(R.drawable.imagedummy))
    }
}