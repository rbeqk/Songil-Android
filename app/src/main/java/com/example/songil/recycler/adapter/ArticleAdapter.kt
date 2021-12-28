package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.ItemArticle
import com.example.songil.databinding.ItemArticleBinding

class ArticleAdapter(baseData : ArrayList<ItemArticle> ?= null) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val dataList = baseData ?: ArrayList<ItemArticle>()

    class ArticleViewHolder(binding : ItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        val root = binding.root
        val thumbnail = binding.ivThumbnail
        val likeImage = binding.ivFavorite
        val likeCnt = binding.tvFavoriteCount
        val title = binding.tvTitle
        val deadLine = binding.tvDate
        val editor = binding.tvWriter
        val bottomLine = binding.lineBottom
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.root.setOnClickListener {

        }
        Glide.with(holder.itemView.context).load(dataList[position].mainImageUrl).into(holder.thumbnail)
        if (dataList[position].isLike == "Y") { holder.likeImage.setImageResource(R.drawable.ic_heart_base_16) }
        else { holder.likeImage.setImageResource(R.drawable.ic_heart_line_16) }
        holder.likeCnt.text = dataList[position].totalLikeCnt.toString()
        holder.deadLine.text = dataList[position].createdAt
        holder.editor.text = dataList[position].editorName
        holder.title.text = dataList[position].title
        if (position == dataList.size - 1){
            holder.bottomLine.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun updateData(newSize : Int){
        notifyItemRangeInserted(dataList.size, newSize)
    }
}