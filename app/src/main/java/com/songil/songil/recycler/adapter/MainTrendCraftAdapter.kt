package com.songil.songil.recycler.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.CraftSimpleInfo
import com.songil.songil.databinding.ItemMainTrendCraftBinding
import com.songil.songil.page_craft.CraftActivity

class MainTrendCraftAdapter(private val context: Context) : RecyclerView.Adapter<MainTrendCraftAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainTrendCraftBinding
    private val dataList = ArrayList<CraftSimpleInfo>()

    class ViewHolder(binding : ItemMainTrendCraftBinding) : RecyclerView.ViewHolder(binding.root){
        val isNew = binding.tvNew
        val craftName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val thumbnail = binding.ivThumbnail
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainTrendCraftBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].mainImageUrl).into(holder.thumbnail)
        holder.craftName.text = dataList[position].name
        holder.artistName.text = dataList[position].artistName
        holder.isNew.visibility = if (dataList[position].isNew == "Y") View.VISIBLE else View.GONE
        holder.root.setOnClickListener {
            val intent = Intent(context, CraftActivity::class.java)
            intent.putExtra(GlobalApplication.CRAFT_IDX, dataList[position].craftIdx)
            (context as BaseActivity<*>).startActivityHorizontal(intent)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<CraftSimpleInfo>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}