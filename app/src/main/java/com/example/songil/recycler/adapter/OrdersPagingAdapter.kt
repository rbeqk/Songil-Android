package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemOrdersBinding
import com.example.songil.page_orderstatus.models.OrderStatusInfo
import com.example.songil.recycler.rv_interface.RvUserOrderStatusView

class OrdersPagingAdapter(private val rvView : RvUserOrderStatusView) : PagingDataAdapter<OrderStatusInfo, OrdersPagingAdapter.OrderStatusViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<OrderStatusInfo>() {
            override fun areItemsTheSame(oldItem: OrderStatusInfo, newItem: OrderStatusInfo): Boolean {
                return oldItem.orderIdx == newItem.orderIdx
            }

            override fun areContentsTheSame(oldItem: OrderStatusInfo, newItem: OrderStatusInfo): Boolean {
                return false
            }
        }
    }

    class OrderStatusViewHolder(binding : ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root){
        val date = binding.tvDate
        val orders = binding.rvOrders
        val separator = binding.lineSeparator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemOrdersBinding.inflate(inflater, parent, false)
        return OrderStatusViewHolder(binding)

    }

    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.date.text = item.createdAt
            holder.orders.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
            holder.orders.adapter = OrderStatusChildAdapter(holder.itemView.context, item.orderDetail, rvView, parentPosition = position)
            if (position == itemCount - 1){
                holder.separator.visibility = View.INVISIBLE
            } else {
                holder.separator.visibility = View.VISIBLE
            }
        }
    }

    // 취소 요청이 성공적으로 호출된 경우 해당 item 상태값 변경
    fun updateOrderItemCancelRequest(parentPosition : Int, childPosition : Int){
        val item = getItem(parentPosition)
        item?.let {
            it.orderDetail[childPosition].canReqCancel = "N"
            it.orderDetail[childPosition].status = 4
            notifyItemChanged(parentPosition)
        }
    }

    // 반품 요청이 성공적으로 호출된 경우 해당 item 상태값 변경
    fun updateOrderItemReturnRequest(parentPosition : Int, childPosition : Int) {
        val item = getItem(parentPosition)
        item?.let {
            it.orderDetail[childPosition].canReqReturn = "N"
            it.orderDetail[childPosition].status = 6
            it.orderDetail[childPosition].commentBtnStatus = 1
            notifyItemChanged(parentPosition)
        }
    }

    // 코멘트 작성이 성공적으로 호출된 경우 해당 item 상태값 변경
    fun updateOrderItemWriteComment(parentPosition : Int, childPosition : Int) {
        val item = getItem(parentPosition)
        item?.let {
            it.orderDetail[childPosition].commentBtnStatus = 3
            notifyItemChanged(parentPosition)
        }
    }
}