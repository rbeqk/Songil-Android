package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.FrontStory
import com.example.songil.databinding.ItemStoryBinding
import com.example.songil.recycler.adapter_interface.SingleUpdateAdapterInterface
import com.example.songil.recycler.rv_interface.RvSingleUpdateView

class WithStoryAdapter(diffCallback : DiffUtil.ItemCallback<FrontStory>) : PagingDataAdapter<FrontStory, WithStoryAdapter.FrontStoryViewHolder>(diffCallback),
    SingleUpdateAdapterInterface {

    override fun onBindViewHolder(holder: FrontStoryViewHolder, position: Int) {
        val storyItem = getItem(position)
        if (storyItem != null){
            Glide.with(holder.itemView.context).load(storyItem.mainImageUrl).into(holder.image)
            if (storyItem.isLike == "Y") holder.isLike.setImageResource(R.drawable.ic_heart_base_16)
            else holder.isLike.setImageResource(R.drawable.ic_heart_line_16)
            holder.likeCount.text = storyItem.totalLikeCnt.toString()
            holder.title.text = storyItem.title
            holder.userName.text = storyItem.userName
            holder.root.setOnClickListener {
                singleUpdateView?.targetItemClick(position, storyItem.storyIdx)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrontStoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoryBinding.inflate(inflater, parent, false)
        return FrontStoryViewHolder(binding)
    }

    class FrontStoryViewHolder(binding : ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val isLike = binding.ivFavorite
        val likeCount = binding.tvFavoriteCount
        val root = binding.root
        val title = binding.tvStoryTitle
        val userName = binding.tvUserName
    }

    override var singleUpdateView: RvSingleUpdateView? = null

    override fun applySingleItemChange(position: Int, target: Any?) {
        if (target is FrontStory){
            val item = getItem(position) as FrontStory
            item.mainImageUrl = target.mainImageUrl
            item.isLike = target.isLike
            item.totalLikeCnt = target.totalLikeCnt
            item.title = target.title
            item.userName = target.userName
            notifyItemChanged(position)
        }
    }
}