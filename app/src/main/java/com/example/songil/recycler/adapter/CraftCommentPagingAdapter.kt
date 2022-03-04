package com.example.songil.recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.songil.R
import com.example.songil.data.Comment
import com.example.songil.databinding.ItemCraftCommentBinding
import com.example.songil.viewPager2.adapter.Vp2ImageAdapter

class CraftCommentPagingAdapter : PagingDataAdapter<Comment, CraftCommentPagingAdapter.ViewHolder>(Comment.commentDiffCallback) {
    class ViewHolder(binding : ItemCraftCommentBinding) : RecyclerView.ViewHolder(binding.root){
        val craftAndArtistName = binding.tvNickname
        val date = binding.tvDate
        val photo = binding.vp2Photo
        val photoCount = binding.tvPage
        val review = binding.tvReview
        val removeBtn = binding.tvReport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCraftCommentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val comment = getItem(position)
        if (comment != null){
            if (comment.isRemoved){
                holder.date.text = ""
                holder.craftAndArtistName.text = comment.artist.userName + "/" + comment.craftName
                holder.photo.visibility = View.GONE
                holder.photoCount.visibility = View.GONE
                holder.removeBtn.visibility = View.INVISIBLE
                holder.review.text = "삭제된 코멘트입니다."
                holder.date.text = "----.--.--"
            } else {
                holder.date.text = comment.createdAt
                holder.review.text = comment.content
                holder.craftAndArtistName.text = comment.artist.userName + "/" + comment.craftName
                holder.removeBtn.text = holder.itemView.context.getString(R.string.remove_with_underline)
                holder.removeBtn.visibility = View.VISIBLE
                val imageCount = comment.commentImageList?.size ?: 0
                if (imageCount != 0){
                    holder.photo.visibility = View.VISIBLE
                    holder.photoCount.visibility = View.VISIBLE
                    holder.photo.adapter = Vp2ImageAdapter(context, comment.commentImageList!!)
                    holder.photo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            holder.photoCount.text = context.getString(R.string.form_count, position + 1, imageCount)
                        }
                    })
                    holder.photoCount.text = context.getString(R.string.form_count, 1, imageCount)
                } else {
                    holder.photo.visibility = View.GONE
                    holder.photoCount.visibility = View.GONE
                }
                holder.removeBtn.setOnClickListener {

                }
            }
        }

    }
}