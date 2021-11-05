package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.databinding.CraftItemImgBinding

class CraftImageAdapter(private val context : Context, private val dataList : ArrayList<String>) : RecyclerView.Adapter<CraftImageAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : CraftItemImgBinding

    class ViewHolder(binding : CraftItemImgBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CraftItemImgBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position]).into(holder.image)
    }

    override fun getItemCount(): Int = dataList.size
}