package com.example.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ItemCancelRequestBinding
import com.example.songil.page_artistmanage.subpage_cancel_request.models.CancelRequest
import com.example.songil.page_payinfo.PayInfoActivity

class CancelRequestItemAdapter(private val dataList : ArrayList<CancelRequest>) : RecyclerView.Adapter<CancelRequestItemAdapter.CancelRequestViewHolder>() {
    inner class CancelRequestViewHolder(binding : ItemCancelRequestBinding) : RecyclerView.ViewHolder(binding.root){
        val separator = binding.lineSeparator
        val image = binding.ivCraft
        val craftName = binding.tvCraftName
        val userName = binding.tvConsumer
        val status = binding.tvOrderStatus
        val reason = binding.tvReason
        val btnCheckUser = binding.btnCheckConsumer
        val btnApprove = binding.btnOk
        val btnDenial = binding.btnDenial
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancelRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCancelRequestBinding.inflate(inflater, parent, false)
        return CancelRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CancelRequestViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(dataList[position].mainImageUrl).into(holder.image)
        holder.craftName.text = dataList[position].name
        holder.userName.text = dataList[position].userName
        holder.reason.text = dataList[position].reason
        when (dataList[position].status){
            1 -> {
                holder.status.text = holder.itemView.context.getString(R.string.request_cancel)
                holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.tomato))
            }
            2 -> {
                holder.status.text = holder.itemView.context.getString(R.string.request_return)
                holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.tomato))
            }
            3 -> {
                holder.status.text = holder.itemView.context.getString(R.string.processing_completed)
                holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.songil_2))
            }
        }
        holder.btnCheckUser.setOnClickListener {
            val intent = Intent(holder.itemView.context, PayInfoActivity::class.java)
            intent.putExtra("IS_ARTIST", true)
            intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
            (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
        }
        holder.btnApprove.isEnabled = (dataList[position].canChangeStatus == "Y")
        holder.btnDenial.isEnabled = (dataList[position].canChangeStatus == "Y")
        holder.btnApprove.setOnClickListener {
            // 여기
        }
        holder.btnDenial.setOnClickListener {
            // 여기
        }
        if (position == itemCount - 1){
            holder.separator.visibility = View.INVISIBLE
        } else {
            holder.separator.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = dataList.size
}