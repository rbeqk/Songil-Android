package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.CraftSimple
import com.example.songil.databinding.ItemCraft3MycommentBinding

class MyWritableCommentPagingAdapter(private val writeFunc : (Int, Int) -> Unit) : PagingDataAdapter<CraftSimple, MyWritableCommentPagingAdapter.ViewHolder>(CraftSimple.craftSimpleDiffCallback) {

    private lateinit var binding : ItemCraft3MycommentBinding
    class ViewHolder(binding : ItemCraft3MycommentBinding) : RecyclerView.ViewHolder(binding.root){
        val productName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val image = binding.ivPhoto
        val writeButton = binding.btnWrite
        val line = binding.lineBottom
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCraft3MycommentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val craft = getItem(position)
        if (craft != null){
            holder.productName.text = craft.craftName
            holder.artistName.text = craft.artist.userName
            Glide.with(holder.itemView.context).load(craft.mainImageUrl).into(holder.image)
            holder.writeButton.setOnClickListener {
                writeFunc(position, craft.orderDetailIdx)
            }
            holder.writeButton.isClickable = craft.commentWritable
            if (craft.commentWritable){
                holder.writeButton.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rectangle_black_radius_4)
                holder.writeButton.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.songil_1))
            }
            else {
                holder.writeButton.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_rectangle_g1_radius_4)
                holder.writeButton.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.g_4))
            }
            if (position == itemCount - 1){
                holder.line.visibility = View.INVISIBLE
            }
        }
    }

    fun changeItem(position : Int){
        val item = getItem(position)
        if (item != null){
            item.commentWritable = false
            notifyItemChanged(position)
        }
    }
}