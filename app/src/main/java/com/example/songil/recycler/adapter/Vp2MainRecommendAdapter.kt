package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.CraftSimpleInfo
import com.example.songil.databinding.ItemMainRecommendVp2Binding

// 임의로 craftSimple 데이터를 사용합니다!! 아직 서버가 없어요
class Vp2MainRecommendAdapter(private val context: Context) : RecyclerView.Adapter<Vp2MainRecommendAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainRecommendVp2Binding
    private val dataList = ArrayList<CraftSimpleInfo>()

    class ViewHolder(binding : ItemMainRecommendVp2Binding) : RecyclerView.ViewHolder(binding.root){
        val img = binding.ivThumbnail
        val craftName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val price = binding.tvPrice
        val background = binding.ivBackground
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainRecommendVp2Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artistName.text = dataList[position].maker
        holder.craftName.text = dataList[position].craftName
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        Glide.with(context).load(dataList[position].thumbnail).into(holder.img)
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<CraftSimpleInfo>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}