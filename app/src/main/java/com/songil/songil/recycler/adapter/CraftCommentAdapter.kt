package com.songil.songil.recycler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.config.ReportTarget
import com.songil.songil.data.CraftComment
import com.songil.songil.viewPager2.adapter.Vp2ImageAdapter
import com.songil.songil.databinding.ItemCraftCommentBinding
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.page_report.ReportActivity

class CraftCommentAdapter(private val context : Context, inputCommentData : ArrayList<CraftComment> ?= null) : RecyclerView.Adapter<CraftCommentAdapter.ViewHolder>(){

    private val commentData = inputCommentData ?: arrayListOf()
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemCraftCommentBinding


    class ViewHolder(binding : ItemCraftCommentBinding) : RecyclerView.ViewHolder(binding.root){
        val nickName = binding.tvNickname
        val date = binding.tvDate
        val photo = binding.vp2Photo
        val photoCount = binding.tvPage
        val review = binding.tvReview
        val reportBtn = binding.tvReport
        val contentLayout = binding.layoutCommentContent
        val reportedLayout = binding.layoutReported
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCraftCommentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageCount = commentData[position].imageUrl?.size ?: 0
        holder.nickName.text = commentData[position].nickname
        holder.date.text = commentData[position].createdAt
        holder.review.text = commentData[position].content
        if (imageCount != 0){
            holder.photo.visibility = View.VISIBLE
            holder.photoCount.visibility = View.VISIBLE
            holder.photo.adapter = Vp2ImageAdapter(context, commentData[position].imageUrl!!)
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
        holder.reportBtn.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                val intent = Intent(context, ReportActivity::class.java)
                intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.CRAFT_COMMENT)
                intent.putExtra(GlobalApplication.TARGET_IDX, commentData[position].commentIdx)
                (holder.itemView.context as CraftActivity).startActivityHorizontal(intent)
            } else {
                (context as BaseActivity<*>).callNeedLoginDialog()
            }
        }
        if (commentData[position].isReported == "Y"){
            holder.contentLayout.visibility = View.INVISIBLE
            holder.reportedLayout.visibility = View.VISIBLE
        } else {
            holder.contentLayout.visibility = View.VISIBLE
            holder.reportedLayout.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = commentData.size

    fun updateData(newDataSize : Int){
        if (newDataSize > 0) {
            notifyItemRangeInserted(commentData.size, newDataSize)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        notifyDataSetChanged()
    }
}