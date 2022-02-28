package com.example.songil.recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.data.DeliveryStatus
import com.example.songil.databinding.ItemDeliveryStatBinding

class DeliveryAdapter(private val dataList : ArrayList<DeliveryStatus> = arrayListOf()) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>(){
    class DeliveryViewHolder(private val binding : ItemDeliveryStatBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : DeliveryStatus){
            binding.data = item
            binding.executePendingBindings()
        }

        val tvDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDeliveryStatBinding.inflate(inflater, parent, false)
        return DeliveryViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.tvDate.text = dataList[position].time[0] + "\n" + dataList[position].time[1]
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newDataList : ArrayList<DeliveryStatus>){
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }
}