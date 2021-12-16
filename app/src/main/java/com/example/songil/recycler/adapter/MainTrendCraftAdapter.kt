package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.data.ProductSimpleInfo
import com.example.songil.databinding.ItemMainTrendCraftBinding

// 임의로 craftSimple 데이터를 사용합니다!! 아직 서버가 없어요
class MainTrendCraftAdapter(private val context: Context) : RecyclerView.Adapter<MainTrendCraftAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainTrendCraftBinding
    private val dataList = ArrayList<ProductSimpleInfo>()

    class ViewHolder(binding : ItemMainTrendCraftBinding) : RecyclerView.ViewHolder(binding.root){
        val isNew = binding.tvNew
        val craftName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val thumbnail = binding.ivThumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainTrendCraftBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].thumbnail).into(holder.thumbnail)
        holder.craftName.text = dataList[position].craftName
        holder.artistName.text = dataList[position].maker
        holder.isNew.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<ProductSimpleInfo>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}