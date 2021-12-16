package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.data.ProductSimpleInfo
import com.example.songil.databinding.ItemCraft3MycommentBinding

class Craft3MyCommentAdapter(private val context: Context, private val dataList : ArrayList<ProductSimpleInfo>) : RecyclerView.Adapter<Craft3MyCommentAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemCraft3MycommentBinding

    class ViewHolder(binding : ItemCraft3MycommentBinding) : RecyclerView.ViewHolder(binding.root){
        val productName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val image = binding.ivPhoto
        val writeButton = binding.btnWrite
        val line = binding.lineBottom
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCraft3MycommentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = dataList[position].craftName
        holder.artistName.text = dataList[position].maker
        Glide.with(context).load(dataList[position].thumbnail).into(holder.image)
        holder.writeButton.setOnClickListener {

        }
        if (position == dataList.size - 1){
            holder.line.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = dataList.size
}