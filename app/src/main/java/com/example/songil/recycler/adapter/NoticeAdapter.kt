package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.data.Notice
import com.example.songil.databinding.ItemNoticeBinding

class NoticeAdapter(private val noticeList : ArrayList<Notice> = arrayListOf()) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
    class NoticeViewHolder(binding : ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root){
        val allowIcon = binding.icTitle
        val title = binding.tvTitle
        val content = binding.tvContent
        val layoutTitle = binding.layoutTitle
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val noticeItem = noticeList[position]
        holder.title.text = noticeItem.title
        holder.content.text = noticeItem.content
        if (noticeItem.isShow == true){
            holder.allowIcon.setImageResource(R.drawable.ic_up)
            holder.content.visibility = View.VISIBLE
            holder.layoutTitle.setOnClickListener {
                noticeItem.isShow = false
                notifyItemChanged(position, "click")
            }
        } else {
            holder.allowIcon.setImageResource(R.drawable.ic_down)
            holder.content.visibility = View.GONE
            holder.layoutTitle.setOnClickListener {
                noticeItem.isShow = true
                notifyItemChanged(position, "click")
            }
        }
    }

    override fun onBindViewHolder(
        holder: NoticeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val noticeItem = noticeList[position]
            if (noticeItem.isShow == true){
                holder.content.visibility = View.VISIBLE
                holder.content.alpha = 0f
                holder.content.animate().alpha(1f).setInterpolator(DecelerateInterpolator()).setDuration(300)
            } else {
                holder.content.visibility = View.GONE
            }

            if (noticeItem.isShow == true){
                holder.allowIcon.setImageResource(R.drawable.ic_up)
                holder.content.visibility = View.VISIBLE
                holder.layoutTitle.setOnClickListener {
                    noticeItem.isShow = false
                    notifyItemChanged(position, "click")
                }
            } else {
                holder.allowIcon.setImageResource(R.drawable.ic_down)
                holder.content.visibility = View.GONE
                holder.layoutTitle.setOnClickListener {
                    noticeItem.isShow = true
                    notifyItemChanged(position, "click")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(inflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun getItemCount(): Int = noticeList.size

    fun applyData(newNoticeList : ArrayList<Notice>){
        noticeList.clear()
        noticeList.addAll(newNoticeList)
        notifyDataSetChanged()
    }

}