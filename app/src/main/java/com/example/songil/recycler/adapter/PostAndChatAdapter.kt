package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.ABTest
import com.example.songil.data.Chat
import com.example.songil.data.Post
import com.example.songil.data.WithQna
import com.example.songil.databinding.ItemPostCommentBinding
import com.example.songil.databinding.ItemPostContentQnaBinding
import com.example.songil.recycler.viewholder.PostCommentViewHolder
import com.example.songil.recycler.viewholder.QnaViewHolder

class PostAndChatAdapter : PagingDataAdapter<Post, RecyclerView.ViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return if (oldItem is Chat && newItem is Chat){
                    oldItem.isDeleted == newItem.isDeleted
                } else {
                    false
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is Chat -> { 0 }
            is WithQna -> { 1 }
            is ABTest -> { 2 }
            else -> { -1 }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            if (holder is PostCommentViewHolder){
                item as Chat
                holder.content.text = item.comment
                holder.userName.text = item.userName
                holder.date.text = item.createdAt
                holder.replies.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
                holder.replies.adapter = ChatReplyAdapter(item.reComment)
                Glide.with(holder.itemView.context).load(item.userProfile).into(holder.profile)
                holder.isWriter.visibility = if (item.isWriter == "Y") View.VISIBLE else View.INVISIBLE
                holder.reportOrRemoveBtn.text = if (item.isUserComment == "Y") holder.itemView.context.getString(R.string.remove_with_underline) else holder.itemView.context.getString(R.string.report_with_underline)
                holder.reportOrRemoveBtn.setOnClickListener {

                }
                holder.replyBtn.setOnClickListener {

                }
            } else if (holder is QnaViewHolder) {
                item as WithQna
                holder.content.text = item.content
                holder.icFav.setImageResource(if (item.isLike == "Y") R.drawable.ic_heart_base_16 else R.drawable.ic_heart_line_16)
                holder.title.text = item.title
                Glide.with(holder.itemView.context).load(item.userProfile).into(holder.profile)
                holder.userName.text = item.userName
                holder.date.text = item.createdAt
                holder.commentCnt.text = item.totalCommentCnt.toString()
                holder.favCnt.text = item.totalLikeCnt.toString()
                holder.favBtn.setOnClickListener {
                    item.isLike = if (item.isLike == "Y") "N" else "Y"
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                val binding = ItemPostCommentBinding.inflate(inflater, parent, false)
                PostCommentViewHolder(binding)
            }
            1 -> {
                val binding = ItemPostContentQnaBinding.inflate(inflater, parent, false)
                QnaViewHolder(binding)
            }
            else -> {
                val binding = ItemPostCommentBinding.inflate(inflater, parent, false)
                PostCommentViewHolder(binding)
            }
        }
    }
}