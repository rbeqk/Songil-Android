package com.songil.songil.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.databinding.ItemPostContentQnaBinding

class QnaViewHolder(binding : ItemPostContentQnaBinding) : RecyclerView.ViewHolder(binding.root) {
    val profile = binding.ivProfile
    val userName = binding.tvName
    val title = binding.tvQnaName
    val commentCnt = binding.tvCommentCount
    val favCnt = binding.tvFavoriteCount
    val date = binding.tvDeadline
    val content = binding.tvContent
    val icFav = binding.ivFavorite
    val favBtn = binding.btnFavorite
}