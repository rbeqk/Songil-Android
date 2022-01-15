package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.data.ChatReply
import com.example.songil.databinding.ItemPostCommentReplyBinding
import com.example.songil.recycler.viewholder.PostCommentReplyViewHolder

class ChatReplyAdapter(private val replyData : ArrayList<ChatReply>) : RecyclerView.Adapter<PostCommentReplyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostCommentReplyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostCommentReplyBinding.inflate(inflater, parent, false)
        return PostCommentReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostCommentReplyViewHolder, position: Int) {
        holder.content.text = replyData[position].comment
        holder.date.text = replyData[position].createdAt
        holder.userName.text = replyData[position].userName
        holder.isWriter.visibility = if (replyData[position].isWriter == "Y") View.VISIBLE else View.INVISIBLE
        holder.reportOrRemoveBtn.text = if (replyData[position].isUserComment == "Y") holder.itemView.context.getString(R.string.remove_with_underline) else holder.itemView.context.getString(R.string.report_with_underline)
        holder.reportOrRemoveBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = replyData.size
}