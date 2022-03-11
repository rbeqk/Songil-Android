package com.songil.songil.viewPager2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.databinding.ItemBannerVp2Binding
import com.songil.songil.page_shop.models.ShopMainBanner

class Vp2BannerAdapter(private val context : Context) : RecyclerView.Adapter<Vp2BannerAdapter.BannerViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemBannerVp2Binding
    private val bannerImageData = ArrayList<ShopMainBanner>()

    class BannerViewHolder(binding : ItemBannerVp2Binding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        binding = ItemBannerVp2Binding.inflate(inflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        Glide.with(context).load(bannerImageData[position].imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int = bannerImageData.size

    fun applyData(newData : ArrayList<ShopMainBanner>){
        bannerImageData.clear()
        bannerImageData.addAll(newData)
        notifyDataSetChanged()
    }
}