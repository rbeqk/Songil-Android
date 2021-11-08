package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.songil.R
import com.example.songil.databinding.CraftItemReviewBinding
import com.example.songil.page_craft.models.CraftReview

class CraftReviewAdapter(private val context : Context, private val dataList : ArrayList<CraftReview>) : RecyclerView.Adapter<CraftReviewAdapter.ViewHolder>(){

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : CraftItemReviewBinding


    class ViewHolder(binding : CraftItemReviewBinding) : RecyclerView.ViewHolder(binding.root){
        val nickName = binding.tvNickname
        val date = binding.tvDate
        val photo = binding.vp2Photo
        val photoCount = binding.tvPage
        val review = binding.tvReview
        val reportBtn = binding.tvReport
        val mainLayout = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CraftItemReviewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageCount = dataList[position].imgs.size
        holder.nickName.text = dataList[position].userNickName
        holder.date.text = dataList[position].date
        holder.review.text = dataList[position].reviewContent
        holder.photo.adapter = CraftViewPager2ImageAdapter(context, dataList[position].imgs)
        if (imageCount != 0){
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
    }

    override fun getItemCount(): Int = dataList.size
}