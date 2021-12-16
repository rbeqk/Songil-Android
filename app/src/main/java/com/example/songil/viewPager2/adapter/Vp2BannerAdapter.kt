package com.example.songil.viewPager2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.databinding.ItemBannerVp2Binding

class Vp2BannerAdapter(private val context : Context) : RecyclerView.Adapter<Vp2BannerAdapter.BannerViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemBannerVp2Binding
    private val bannerImageData = ArrayList<String>()

    class BannerViewHolder(binding : ItemBannerVp2Binding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        binding = ItemBannerVp2Binding.inflate(inflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        Glide.with(context).load(bannerImageData[position]).into(holder.image)
    }

    override fun getItemCount(): Int = bannerImageData.size

    fun applyData(newData : ArrayList<String>){
        bannerImageData.clear()
        bannerImageData.addAll(newData)
        notifyDataSetChanged()
    }
}