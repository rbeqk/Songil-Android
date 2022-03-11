package com.songil.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.R
import com.songil.songil.data.ChatReply
import com.songil.songil.databinding.ItemPostCommentReplyBinding
import com.songil.songil.recycler.rv_interface.RvPostAndChatView
import com.songil.songil.recycler.viewholder.PostCommentReplyViewHolder

class ChatReplyAdapter(private val replyData : ArrayList<ChatReply>, private val view : RvPostAndChatView) : RecyclerView.Adapter<PostCommentReplyViewHolder>(){
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
            if (replyData[position].isUserComment == "Y"){
                view.removeChat(replyData[position].commentIdx)
            } else {
                view.reportChat(replyData[position].commentIdx)
            }
        }
    }

    override fun getItemCount(): Int = replyData.size
}