package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.data.WithQna
import com.example.songil.databinding.ItemPostBinding

class PostAdapter(diffCallback : DiffUtil.ItemCallback<WithQna>) : PagingDataAdapter<WithQna, PostAdapter.WithQnaViewHolder>(diffCallback){

    override fun onBindViewHolder(holder: WithQnaViewHolder, position: Int) {
        val qnaItem = getItem(position)
        if (qnaItem != null){
            holder.title.text = qnaItem.title
            holder.date.text = qnaItem.date
            holder.commentCount.text = qnaItem.commentCount.toString()
            holder.likeCount.text = qnaItem.likeCount.toString()
            holder.userName.text = qnaItem.name
            if (qnaItem.isLike) holder.isLike.setImageResource(R.drawable.ic_heart_base_16)
            else holder.isLike.setImageResource(R.drawable.ic_heart_line_16)
            holder.content.text = qnaItem.content
            holder.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithQnaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return WithQnaViewHolder(binding)
    }

    class WithQnaViewHolder(binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val commentCount = binding.tvChatCount
        val likeCount = binding.tvFavoriteCount
        val content = binding.tvContent
        val userName = binding.tvUser
        val date = binding.tvDate
        val isLike = binding.ivFavorite
        val root = binding.root
    }

}