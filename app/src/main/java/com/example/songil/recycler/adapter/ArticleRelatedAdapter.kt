package com.example.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.SimpleArticle
import com.example.songil.databinding.ItemArticleRelatedBinding
import com.example.songil.page_articlecontent.ArticleContentActivity

class ArticleRelatedAdapter : RecyclerView.Adapter<ArticleRelatedAdapter.ArticleRelatedViewHolder>(){

    private val relatedArticleData = ArrayList<SimpleArticle>()

    class ArticleRelatedViewHolder(binding : ItemArticleRelatedBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val image = binding.ivPhoto
        val root = binding.root
        val editor = binding.tvEditor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleRelatedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleRelatedBinding.inflate(inflater, parent, false)
        return ArticleRelatedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleRelatedViewHolder, position: Int) {
        if (position < relatedArticleData.size){
            holder.title.text = relatedArticleData[position].title
            holder.editor.text = relatedArticleData[position].editorName
            Glide.with(holder.itemView.context).load(relatedArticleData[position].mainImageUrl).into(holder.image)
            holder.root.setOnClickListener {
                val intent = Intent(holder.itemView.context, ArticleContentActivity::class.java)
                intent.putExtra(GlobalApplication.ARTICLE_IDX, relatedArticleData[position].articleIdx)
                (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
            }
        }
    }

    override fun getItemCount(): Int = 3

    fun applyData(newData : ArrayList<SimpleArticle>){
        relatedArticleData.clear()
        relatedArticleData.addAll(newData)
        notifyDataSetChanged()
    }
}