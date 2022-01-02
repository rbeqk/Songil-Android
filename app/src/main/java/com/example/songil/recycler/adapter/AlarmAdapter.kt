package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.data.WithNotice
import com.example.songil.databinding.ItemAlarmBinding

class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.NoticeViewHolder>(){

    private val noticeData = ArrayList<WithNotice>()

    class NoticeViewHolder(binding : ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.tvName
        val notice = binding.tvNotice
        val deadline = binding.tvDeadline
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlarmBinding.inflate(inflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.name.text = noticeData[position].userName
        holder.notice.text = noticeData[position].notice
        holder.deadline.text = noticeData[position].deadline
    }

    override fun getItemCount(): Int = noticeData.size

    fun applyData(newData : ArrayList<WithNotice>){
        noticeData.clear()
        noticeData.addAll(newData)
        notifyDataSetChanged()
    }

}