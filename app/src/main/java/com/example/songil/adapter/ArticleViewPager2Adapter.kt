package com.example.songil.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.data.Article
import com.example.songil.databinding.ArticleItemArticleBinding

class ArticleViewPager2Adapter(private val context: Context) : RecyclerView.Adapter<ArticleViewPager2Adapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ArticleItemArticleBinding
    private val articleList = ArrayList<Article>()

    class ViewHolder(binding : ArticleItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        val thumbnail = binding.ivThumbnail
        val articleTitle = binding.tvArticleTitle
        val articleEditor = binding.tvArticleEditor
        val articleType = binding.tvArticleType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ArticleItemArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.articleEditor.text = articleList[position].editor
        holder.articleTitle.text = articleList[position].articleTitle
        Glide.with(context).load(articleList[position].imageURL).into(holder.thumbnail)
        holder.articleType.text = articleList[position].articleType
    }

    override fun getItemCount(): Int = articleList.size

    fun applyData(inputData : ArrayList<Article>){
        articleList.clear()
        articleList.addAll(inputData)
        notifyDataSetChanged()
    }
}