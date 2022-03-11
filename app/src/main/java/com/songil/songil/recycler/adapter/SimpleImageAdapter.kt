package com.songil.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.databinding.ItemImageBinding

class SimpleImageAdapter(private val context : Context, private val dataList : ArrayList<String>) : RecyclerView.Adapter<SimpleImageAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemImageBinding

    class ViewHolder(binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position]).into(holder.image)
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<String>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}