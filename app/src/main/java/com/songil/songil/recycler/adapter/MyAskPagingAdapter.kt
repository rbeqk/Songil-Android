package com.songil.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.ItemMyAskBinding
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.page_mypage_ask_history.models.MyPageAskTotalData

class MyAskPagingAdapter : PagingDataAdapter<MyPageAskTotalData, MyAskPagingAdapter.MyPageAskViewHolder>(diffCallback = diffCallback) {

    inner class MyPageAskViewHolder(private val binding : ItemMyAskBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : MyPageAskTotalData) {
            binding.askData = item
        }
        val lineMiddle = binding.lineMiddle
        val layoutAnswer = binding.layoutAnswer
        val layoutAsk = binding.layoutAsk
        val btnCraft = binding.btnCraft
        val answerStatus = binding.tvAnswerStatus
        val tvArtistName = binding.tvArtistName
        val tvAnswerContent = binding.tvAnswerContent
        val tvDate = binding.tvAnswerDate
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MyPageAskTotalData>(){
            override fun areItemsTheSame(oldItem: MyPageAskTotalData, newItem: MyPageAskTotalData): Boolean {
                return (oldItem.askIdx == newItem.askIdx)
            }

            override fun areContentsTheSame(oldItem: MyPageAskTotalData, newItem: MyPageAskTotalData): Boolean {
                return (oldItem.ask.status == newItem.ask.status)
            }

        }
    }

    override fun onBindViewHolder(holder: MyPageAskViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item)
            holder.btnCraft.setOnClickListener {
                val intent = Intent(holder.itemView.context, CraftActivity::class.java)
                intent.putExtra(GlobalApplication.CRAFT_IDX, item.ask.craftIdx)
                (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
            }
            if (item.ask.status == 1){
                holder.lineMiddle.visibility = View.GONE
                holder.layoutAnswer.visibility = View.GONE
                holder.answerStatus.setBackgroundResource(R.drawable.shape_rectangle_g2_radius_50)
                holder.answerStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.songil_2))
                holder.answerStatus.text = holder.itemView.context.getString(R.string.wait_answer)
            } else {
                holder.lineMiddle.visibility = View.VISIBLE
                holder.layoutAnswer.visibility = View.VISIBLE
                holder.answerStatus.setBackgroundResource(R.drawable.shape_rectangle_black_radius_50)
                holder.answerStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.songil_1))
                holder.answerStatus.text = holder.itemView.context.getString(R.string.complete_answer)
                item.answer?.let {
                    holder.tvAnswerContent.text = it.content
                    holder.tvDate.text = it.createdAt
                    holder.tvArtistName.text = it.artistName
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageAskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyAskBinding.inflate(inflater, parent, false)
        return MyPageAskViewHolder(binding)
    }
}