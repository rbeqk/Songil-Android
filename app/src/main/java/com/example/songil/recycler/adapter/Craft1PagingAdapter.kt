package com.example.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft1
import com.example.songil.databinding.ItemCraft1Binding
import com.example.songil.page_craft.CraftActivity

class Craft1PagingAdapter() : PagingDataAdapter<Craft1, Craft1PagingAdapter.Craft1ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Craft1>(){
            override fun areItemsTheSame(oldItem: Craft1, newItem: Craft1): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Craft1, newItem: Craft1): Boolean {
                return (oldItem.name == newItem.name && oldItem.isSoldOut == newItem.isSoldOut
                        && oldItem.isLike == newItem.isLike
                        && oldItem.totalLikeCnt == newItem.totalLikeCnt
                        && oldItem.totalCommentCnt == newItem.totalCommentCnt)
            }

        }
    }

    class Craft1ViewHolder(binding : ItemCraft1Binding) : RecyclerView.ViewHolder(binding.root){
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

    override fun onBindViewHolder(holder: Craft1ViewHolder, position: Int) {
        val craft = getItem(position)
        if (craft != null){
            holder.layoutMain.setOnClickListener {
                val intent = Intent(holder.itemView.context, CraftActivity::class.java)
                intent.putExtra(GlobalApplication.CRAFT_IDX, craft.craftIdx)
                (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
            }
            Glide.with(holder.itemView.context).load(craft.mainImageUrl).into(holder.image)
            holder.craftName.text = craft.name
            holder.artistName.text = craft.artistName
            holder.price.text = holder.itemView.context.getString(R.string.form_price_won, craft.price)
            holder.favCount.text = craft.totalLikeCnt.toString()
            holder.review.text = holder.itemView.context.getString(R.string.form_review_count, craft.totalCommentCnt)

            when {
                (craft.isSoldOut == "Y") -> {
                    holder.isNew.text = holder.itemView.context.getString(R.string.soldout_eng)
                    holder.isNew.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.g_3))
                }
                (craft.isNew == "Y") -> {
                    holder.isNew.visibility = View.VISIBLE
                }
                else -> {
                    holder.isNew.visibility = View.GONE
                }
            }
            if (craft.isLike == "N"){
                holder.ivFav.setImageResource(R.drawable.ic_heart_line_16)
            } else {
                holder.ivFav.setImageResource(R.drawable.ic_heart_base_16)
            }
            holder.btnFav.setOnClickListener {
                //view.clickLike(craft.craftIdx, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Craft1ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCraft1Binding.inflate(inflater, parent, false)
        return Craft1ViewHolder(binding)

    }
}