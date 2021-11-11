package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.databinding.ShopItemCraftSimpleBinding
import com.example.songil.page_shop.shop_category.models.CraftSimple
import com.example.songil.recycler.rv_interface.RvCraftView

class RvCraftSimpleAdapter(private val context: Context, private val view : RvCraftView) : RecyclerView.Adapter<RvCraftSimpleAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ShopItemCraftSimpleBinding
    private val dataList = ArrayList<CraftSimple>()

    class ViewHolder(binding : ShopItemCraftSimpleBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val maker = binding.tvMaker
        val price = binding.tvPrice
        val layoutMain = binding.root
        var craftName = binding.tvCraft
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ShopItemCraftSimpleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].thumbnailImg).into(holder.image)
        holder.maker.text = dataList[position].artistName
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        holder.layoutMain.setOnClickListener {
            view.craftClick(dataList[position].productIdx)
        }
        holder.craftName.text = dataList[position].productName
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<CraftSimple>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}