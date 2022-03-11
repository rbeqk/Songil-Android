package com.songil.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.ItemOrderStatusArtistBinding
import com.songil.songil.page_artistmanage.subpage_orderstat.models.OrderStatusArtistItemInfo
import com.songil.songil.page_payinfo.PayInfoActivity
import com.songil.songil.page_shippinginfo_confirmation.ShippingInfoConfirmationActivity
import com.songil.songil.recycler.rv_interface.RvArtistOrderStatusView

class OrderStatusArtistChildAdapter(private val dataList : ArrayList<OrderStatusArtistItemInfo>, private val view : RvArtistOrderStatusView, private val parentPosition : Int = 0) : RecyclerView.Adapter<OrderStatusArtistChildAdapter.OrderStatusArtistViewHolder>() {
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
            val intent = Intent(holder.itemView.context, PayInfoActivity::class.java)
            intent.putExtra("IS_ARTIST", true)
            intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
            (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
        }
        when (dataList[position].status){
            1 -> {
                holder.orderStatus.text = holder.itemView.context.getString(R.string.before_shipment)
                holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.tomato))
                holder.btnInputShippingInfo.text = holder.itemView.context.getString(R.string.input_shipping_information)
                holder.btnInputShippingInfo.setOnClickListener {
                    view.goToInputShippingInfo(parentPosition, position, dataList[position].orderDetailIdx)
                }
            }
            2 -> {
                holder.orderStatus.text = holder.itemView.context.getString(R.string.sending_out)
                holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                holder.btnInputShippingInfo.text = holder.itemView.context.getString(R.string.confirmation_shipping_info)
                holder.btnInputShippingInfo.setOnClickListener {
                    val intent = Intent(holder.itemView.context, ShippingInfoConfirmationActivity::class.java)
                    intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
                    (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                }
            }
            3 -> {
                holder.orderStatus.text = holder.itemView.context.getString(R.string.complete_shipment)
                holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.azul))
                holder.btnInputShippingInfo.text = holder.itemView.context.getString(R.string.confirmation_shipping_info)
                holder.btnInputShippingInfo.setOnClickListener {
                    val intent = Intent(holder.itemView.context, ShippingInfoConfirmationActivity::class.java)
                    intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
                    (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
}