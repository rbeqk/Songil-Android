package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.data.Orders
import com.example.songil.databinding.ItemOrdersBinding

class OrdersAdapter(private val context : Context) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemOrdersBinding
    private val dataList = ArrayList<Orders>()

    class ViewHolder(binding : ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root){
        val date = binding.tvDate
        val orders = binding.rvOrders
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemOrdersBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = dataList[position].date
        holder.orders.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.orders.adapter = OrderStatusAdapter(context, dataList[position].orderList)
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<Orders>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}