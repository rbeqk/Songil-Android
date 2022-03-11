package com.songil.songil.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.databinding.ItemPostCommentBinding
import com.songil.songil.databinding.ItemPostCommentReplyBinding

class PostCommentViewHolder(binding : ItemPostCommentBinding) : RecyclerView.ViewHolder(binding.root) {
    val profile = binding.ivProfile
    val userName = binding.tvName
    val content = binding.tvContent
    val replies = binding.rvReply
    val date = binding.tvDeadline
    val replyBtn = binding.btnReply
    val isWriter = binding.tvIsWriter
    val reportOrRemoveBtn = binding.btnReport
}

class PostCommentReplyViewHolder(binding : ItemPostCommentReplyBinding) : RecyclerView.ViewHolder(binding.root){
    val userName = binding.tvName
    val content = binding.tvReply
    val date = binding.tvDeadline
    val reportOrRemoveBtn = binding.btnReport
    val isWriter = binding.tvIsWriter
}