package com.songil.songil.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.databinding.ItemAbtestBinding

class AbTestViewHolder(binding : ItemAbtestBinding) : RecyclerView.ViewHolder(binding.root){
    val artistImage = binding.ivProfile
    val artistName = binding.tvName
    val date = binding.tvDeadline
    val content = binding.tvContent
    val commentCount = binding.tvCommentCount
    val photoA = binding.ivPhotoA
    val photoB = binding.ivPhotoB
    val voteBtn = binding.btnVote
    val layoutA = binding.selectPhotoA
    val rateA = binding.tvRateA
    val checkImageA = binding.ivCheckA
    val layoutB = binding.selectPhotoB
    val rateB = binding.tvRateB
    val checkImageB = binding.ivCheckB
    val root = binding.root
}