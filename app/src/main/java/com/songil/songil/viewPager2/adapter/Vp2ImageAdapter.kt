package com.songil.songil.viewPager2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.databinding.ItemImageVp2Binding

class Vp2ImageAdapter(private val context : Context, private val dataList : ArrayList<String>) : RecyclerView.Adapter<Vp2ImageAdapter.ViewHolder>(){
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemImageVp2Binding

    class ViewHolder(binding : ItemImageVp2Binding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemImageVp2Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position]).into(holder.image)
    }

    override fun getItemCount(): Int = dataList.size
}