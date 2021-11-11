package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.databinding.CraftItemImgBinding
import com.example.songil.page_shop.models.NewCraft
import com.example.songil.recycler.rv_interface.RvCraftView

class RvImageAdapter(private val context : Context, private val view : RvCraftView) : RecyclerView.Adapter<RvImageAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : CraftItemImgBinding
    private val dataList = ArrayList<NewCraft>()

    class ViewHolder(binding : CraftItemImgBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
        val main = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CraftItemImgBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].thumbNailImg).into(holder.image)
        holder.main.setOnClickListener {
            view.craftClick(dataList[position].productIdx)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<NewCraft>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}