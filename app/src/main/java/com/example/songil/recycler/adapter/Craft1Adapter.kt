package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.databinding.ItemCraft1Binding
import com.example.songil.page_shop.shop_category.models.CraftDetail
import com.example.songil.recycler.rv_interface.RvCraftLikeView

class Craft1Adapter(private val context: Context, private val view : RvCraftLikeView<Int>) : RecyclerView.Adapter<Craft1Adapter.ViewHolder>() {

    private lateinit var binding : ItemCraft1Binding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val dataList = ArrayList<CraftDetail>()

    class ViewHolder(binding : ItemCraft1Binding) : RecyclerView.ViewHolder(binding.root){
        val layoutMain = binding.root
        val image = binding.ivPhoto
        val isNew = binding.tvIsNew
        val craftName = binding.tvName
        val artistName = binding.tvMaker
        val price = binding.tvPrice
        val btnFav = binding.btnFavorite
        val favCount = binding.tvFavoriteCount
        val ivFav = binding.ivFavorite
        val review = binding.tvReviewCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCraft1Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layoutMain.setOnClickListener {
            view.clickData(dataList[position].productIdx)
        }
        //Glide.with(context).load(dataList[position].thumbnailImg).into(holder.image)
        holder.craftName.text = dataList[position].productName
        holder.artistName.text = dataList[position].artistName
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        holder.favCount.text = dataList[position].likeCount.toString()
        holder.review.text = context.getString(R.string.form_review_count, dataList[position].reviewCount)
        if (dataList[position].newOrNot == "NEW"){
            holder.isNew.visibility = View.VISIBLE
        } else {
            holder.isNew.visibility = View.GONE
        }
        if (dataList[position].likeOrNot == 0){
            holder.ivFav.setImageResource(R.drawable.ic_heart_line_16)
        } else {
            holder.ivFav.setImageResource(R.drawable.ic_heart_base_16)
        }
        holder.btnFav.setOnClickListener {
            view.clickLike(dataList[position].productIdx, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<CraftDetail>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun clearData(){
        val prevSize = dataList.size
        dataList.clear()
        notifyItemRangeRemoved(0, prevSize)
    }

    fun applyLike(position : Int, isLike : Int, likeCount : Int){
        dataList[position].likeOrNot = isLike
        dataList[position].likeCount = likeCount
        notifyItemChanged(position)
    }

    fun updateList(item : ArrayList<CraftDetail>?){
        if (item != null) {
            val prevSize = dataList.size
            val newSize = item.size
            val addCount = newSize - prevSize
            dataList.clear()
            dataList.addAll(item)
            notifyItemRangeInserted(prevSize, addCount)
        }
    }
}