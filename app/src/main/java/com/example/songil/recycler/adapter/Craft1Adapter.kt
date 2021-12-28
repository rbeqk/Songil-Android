package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.data.Craft1
import com.example.songil.databinding.ItemCraft1Binding
import com.example.songil.recycler.rv_interface.RvCraftLikeView

class Craft1Adapter(private val context: Context, private val view : RvCraftLikeView<Int>, baseData : ArrayList<Craft1> ?= null) : RecyclerView.Adapter<Craft1Adapter.ViewHolder>() {

    private lateinit var binding : ItemCraft1Binding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val dataList = baseData ?: ArrayList<Craft1>()
    private val useReference = (baseData != null) // pass by reference 로 전달받은 경우 true


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
            view.clickData(dataList[position].craftIdx)
        }
        //Glide.with(context).load(dataList[position].thumbnailImg).into(holder.image)
        holder.craftName.text = dataList[position].name
        holder.artistName.text = dataList[position].artistName
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        holder.favCount.text = dataList[position].totalLikeCnt.toString()
        holder.review.text = context.getString(R.string.form_review_count, dataList[position].totalCommentCnt)

        when {
            (dataList[position].isSoldOut == "Y") -> {
                holder.isNew.text = holder.itemView.context.getString(R.string.soldout_eng)
                holder.isNew.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.g_3))
            }
            (dataList[position].isNew == "Y") -> {
                holder.isNew.visibility = View.VISIBLE
            }
            else -> {
                holder.isNew.visibility = View.GONE
            }
        }
        if (dataList[position].isLike == "N"){
            holder.ivFav.setImageResource(R.drawable.ic_heart_line_16)
        } else {
            holder.ivFav.setImageResource(R.drawable.ic_heart_base_16)
        }
        holder.btnFav.setOnClickListener {
            view.clickLike(dataList[position].craftIdx, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<Craft1>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun clearData(){
        val prevSize = dataList.size
        dataList.clear()
        notifyItemRangeRemoved(0, prevSize)
    }

    fun applyLike(position : Int, isLike : String, likeCount : Int){
        if (useReference){
            notifyItemChanged(position)
        } else {
            dataList[position].isLike = isLike
            dataList[position].totalLikeCnt = likeCount
            notifyItemChanged(position)
        }
    }

    fun updateList(item : ArrayList<Craft1>?){
        if (item != null) {
            val prevSize = dataList.size
            val newSize = item.size
            val addCount = newSize - prevSize
            dataList.clear()
            dataList.addAll(item)
            notifyItemRangeInserted(prevSize, addCount)
        }
    }

    // pass by reference 의 경우, viewModel 상에서 데이터를 수정하면 여기에도 반영되므로 notifyItemRangeInserted 만 호출
    fun updateListRef(addItemSize : Int){
        notifyItemRangeInserted(dataList.size, addItemSize)
    }
}