package com.songil.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.data.Ask
import com.songil.songil.databinding.ItemCraft3ArtistBinding

class Craft3ArtistAdapter(private val onclick : (Int, Int, Int) -> Unit) : PagingDataAdapter<Ask, Craft3ArtistAdapter.Craft3ArtistViewHolder>(diffCallback){

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Ask>() {
            override fun areItemsTheSame(oldItem: Ask, newItem: Ask): Boolean {
                return (oldItem.askIdx == newItem.askIdx)
            }

            override fun areContentsTheSame(oldItem: Ask, newItem: Ask): Boolean {
                return (oldItem.status == newItem.status)
            }
        }
    }

    class Craft3ArtistViewHolder(binding : ItemCraft3ArtistBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivCraft
        val name = binding.tvCraftName
        val artistName = binding.tvNickname
        val date = binding.tvDate
        val writeBtn = binding.tvAnswer
    }

    override fun onBindViewHolder(holder: Craft3ArtistViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.name.text = data.name
            holder.artistName.text = data.nickname
            holder.date.text = data.createdAt
            Glide.with(holder.itemView.context).load(data.mainImageUrl).into(holder.image)
            if (data.status == 1){
                holder.writeBtn.text = holder.itemView.context.getString(R.string.do_answer)
                holder.writeBtn.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.songil_2))
            } else {
                holder.writeBtn.text = holder.itemView.context.getString(R.string.complete_answer)
                holder.writeBtn.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.g_3))
            }
            holder.writeBtn.setOnClickListener {
                onclick(position, data.askIdx, data.status)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Craft3ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCraft3ArtistBinding.inflate(inflater, parent, false)
        return Craft3ArtistViewHolder(binding)
    }

    fun applyChange(position : Int){
        getItem(position)?.status = 2
        notifyItemChanged(position)
    }
}