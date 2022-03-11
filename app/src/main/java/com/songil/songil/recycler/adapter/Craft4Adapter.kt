package com.songil.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.data.Craft4
import com.songil.songil.databinding.ItemCraft4Binding

class Craft4Adapter : RecyclerView.Adapter<Craft4Adapter.Craft4ViewHolder>(){

    private val craft4Data = ArrayList<Craft4>()

    class Craft4ViewHolder (binding :ItemCraft4Binding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val craftName = binding.tvCraft
        val artistName = binding.tvMaker
        val price = binding.tvPrice
        val count =binding.tvCount
        val line = binding.lineBottom
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Craft4ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCraft4Binding.inflate(inflater, parent, false)
        return Craft4ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Craft4ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(craft4Data[position].mainImageUrl).into(holder.image)
        holder.craftName.text = craft4Data[position].name
        holder.artistName.text = craft4Data[position].artistName
        holder.count.text = holder.itemView.context.getString(R.string.form_single_count, craft4Data[position].amount)
        holder.price.text = holder.itemView.context.getString(R.string.form_price_won, craft4Data[position].price)
        if (position == itemCount - 1){
            holder.line.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = craft4Data.size

    fun applyData(newData : ArrayList<Craft4>){
        craft4Data.clear()
        craft4Data.addAll(newData)
        notifyDataSetChanged()
    }
}