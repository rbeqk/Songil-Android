package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.databinding.ItemOrderStatusArtistBinding
import com.example.songil.page_artistmanage.subpage_orderstat.models.OrderStatusArtistItemInfo

class OrderStatusArtistAdapter(private val dataList : ArrayList<OrderStatusArtistItemInfo>) : RecyclerView.Adapter<OrderStatusArtistAdapter.OrderStatusArtistViewHolder>() {
    class OrderStatusArtistViewHolder(binding : ItemOrderStatusArtistBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivCraft
        val craftName = binding.tvCraftName
        val userName = binding.tvConsumer
        val orderStatus = binding.tvOrderStatus
        val btnCheckUser = binding.btnCheckConsumer
        val btnInputShippingInfo = binding.btnShippingInformation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderStatusArtistBinding.inflate(inflater, parent, false)
        return OrderStatusArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderStatusArtistViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(dataList[position].mainImageUrl).into(holder.image)
        holder.craftName.text = dataList[position].name
        holder.userName.text = dataList[position].userName
        holder.btnCheckUser.setOnClickListener {

        }
        holder.btnInputShippingInfo.setOnClickListener {

        }
        when (dataList[position].status){
            1 -> {
                holder.orderStatus.text = holder.itemView.context.getString(R.string.before_shipment)
                holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.tomato))
            }
            2 -> {
                holder.orderStatus.text = holder.itemView.context.getString(R.string.sending_out)
                holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
            }
            3 -> {
                holder.orderStatus.text = holder.itemView.context.getString(R.string.complete_shipment)
                holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.azul))
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
}