package com.example.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.WithQna
import com.example.songil.databinding.ItemPostBinding
import com.example.songil.page_qna.QnaActivity
import com.example.songil.page_story.StoryActivity

class PostAdapter(diffCallback : DiffUtil.ItemCallback<WithQna>, private val postType : Int = 0) : PagingDataAdapter<WithQna, PostAdapter.WithQnaViewHolder>(diffCallback){

    override fun onBindViewHolder(holder: WithQnaViewHolder, position: Int) {
        val qnaItem = getItem(position)
        if (qnaItem != null){
            holder.title.text = qnaItem.title
            holder.date.text = qnaItem.createdAt
            holder.commentCount.text = qnaItem.totalCommentCnt.toString()
            holder.likeCount.text = qnaItem.totalLikeCnt.toString()
            holder.userName.text = qnaItem.userName
            if (qnaItem.isLike == "Y") holder.isLike.setImageResource(R.drawable.ic_heart_base_16)
            else holder.isLike.setImageResource(R.drawable.ic_heart_line_16)
            holder.content.text = qnaItem.content
            holder.root.setOnClickListener {
                if (postType == 0 ){
                    val intent = Intent(holder.itemView.context, QnaActivity::class.java)
                    intent.putExtra("idx",1)
                    (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                }
                else {
                    val intent = Intent(holder.itemView.context, StoryActivity::class.java)
                    intent.putExtra(GlobalApplication.STORY_IDX,1)
                    (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                }
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