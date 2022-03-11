package com.songil.songil.viewPager2.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.CraftSimpleInfo
import com.songil.songil.databinding.ItemMainRecommendVp2Binding
import com.songil.songil.page_craft.CraftActivity

class Vp2MainRecommendAdapter(private val context: Context) : RecyclerView.Adapter<Vp2MainRecommendAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainRecommendVp2Binding
    private val dataList = ArrayList<CraftSimpleInfo>()

    class ViewHolder(binding : ItemMainRecommendVp2Binding) : RecyclerView.ViewHolder(binding.root){
        val img = binding.ivThumbnail
        val craftName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val price = binding.tvPrice
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainRecommendVp2Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artistName.text = dataList[position].artistName
        holder.craftName.text = dataList[position].name
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        Glide.with(context).load(dataList[position].mainImageUrl).error(R.drawable.logo_songil).into(holder.img)
        holder.img.setColorFilter(Color.argb(74, 0, 0, 0), PorterDuff.Mode.SRC_ATOP)
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