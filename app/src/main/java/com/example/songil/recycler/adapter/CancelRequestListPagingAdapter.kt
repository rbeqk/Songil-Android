package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemOrdersBinding
import com.example.songil.page_artistmanage.subpage_cancel_request.models.GetCancelRequestItemResponseBody

class CancelRequestListPagingAdapter : PagingDataAdapter<GetCancelRequestItemResponseBody, CancelRequestListPagingAdapter.OrderStatusViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<GetCancelRequestItemResponseBody>() {
            override fun areItemsTheSame(oldItem: GetCancelRequestItemResponseBody, newItem: GetCancelRequestItemResponseBody): Boolean {
                return oldItem.order[0].orderDetailIdx == newItem.order[0].orderDetailIdx
            }

            override fun areContentsTheSame(oldItem: GetCancelRequestItemResponseBody, newItem: GetCancelRequestItemResponseBody): Boolean {
                return false
            }
        }
    }

    class OrderStatusViewHolder(binding : ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root){
        val date = binding.tvDate
        val orders = binding.rvOrders
        val separator = binding.lineSeparator
    }

    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.date.text = item.createdAt
            holder.orders.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
            holder.orders.adapter = CancelRequestItemAdapter(item.order)
            if (position == itemCount - 1){ holder.separator.visibility = View.INVISIBLE }
            else { holder.separator.visibility = View.VISIBLE }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrdersBinding.inflate(inflater, parent, false)
        return OrderStatusViewHolder(binding)
    }
}