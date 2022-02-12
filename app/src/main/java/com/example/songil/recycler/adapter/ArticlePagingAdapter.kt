package com.example.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.ItemArticle
import com.example.songil.databinding.ItemArticleBinding
import com.example.songil.page_articlecontent.ArticleContentActivity

class ArticlePagingAdapter : PagingDataAdapter<ItemArticle, ArticlePagingAdapter.ArticleViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ItemArticle>(){
            override fun areItemsTheSame(oldItem: ItemArticle, newItem: ItemArticle): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemArticle, newItem: ItemArticle): Boolean {
                return (oldItem.isLike == newItem.isLike && oldItem.totalLikeCnt == newItem.totalLikeCnt && oldItem.title == newItem.title)
            }
        }
    }

    inner class ArticleViewHolder(binding : ItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        val root = binding.root
        val thumbnail = binding.ivThumbnail
        val likeImage = binding.ivFavorite
        val likeCnt = binding.tvFavoriteCount
        val title = binding.tvTitle
        val deadLine = binding.tvDate
        val editor = binding.tvWriter
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleItem = getItem(position)
        if (articleItem != null) {
            holder.root.setOnClickListener {
                val intent = Intent(holder.itemView.context, ArticleContentActivity::class.java)
                intent.putExtra(GlobalApplication.ARTICLE_IDX, articleItem.articleIdx)
                (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
            }
            Glide.with(holder.itemView.context).load(articleItem.mainImageUrl).into(holder.thumbnail)
            if (articleItem.isLike == "Y") {
                holder.likeImage.setImageResource(R.drawable.ic_heart_base_16)
            } else {
                holder.likeImage.setImageResource(R.drawable.ic_heart_line_16)
            }
            holder.likeCnt.text = articleItem.totalLikeCnt.toString()
            holder.deadLine.text = articleItem.createdAt
            holder.editor.text = articleItem.editorName
            holder.title.text = articleItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }
}