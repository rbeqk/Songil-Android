package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.data.WithQna
import com.example.songil.databinding.ItemPostBinding
import com.example.songil.recycler.adapter_interface.SingleUpdateAdapterInterface
import com.example.songil.recycler.rv_interface.RvSingleUpdateView

class WithQnaPagingAdapter : PagingDataAdapter<WithQna, WithQnaPagingAdapter.WithQnaViewHolder>(diffCallback), SingleUpdateAdapterInterface{

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<WithQna>(){
            override fun areItemsTheSame(oldItem: WithQna, newItem: WithQna): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: WithQna, newItem: WithQna): Boolean {
                return (oldItem.isLike == newItem.isLike) && (oldItem.totalLikeCnt == newItem.totalLikeCnt)
            }

        }
    }

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
                singleUpdateView?.targetItemClick(position, qnaItem.qnaIdx)
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

    override var singleUpdateView: RvSingleUpdateView? = null

    override fun applySingleItemChange(position: Int, target: Any?) {
        if (target is WithQna){
            val item = getItem(position) as WithQna
            item.userProfile = target.userProfile
            item.userName = target.userName
            item.title = target.title
            item.content = target.content
            item.totalLikeCnt = target.totalLikeCnt
            item.totalCommentCnt = target.totalCommentCnt
            item.isLike = target.isLike
            notifyItemChanged(position)
        }
    }

}