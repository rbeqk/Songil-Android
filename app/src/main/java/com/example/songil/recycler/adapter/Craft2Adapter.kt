package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.Craft2
import com.example.songil.databinding.ItemCraft2Binding
import com.example.songil.recycler.rv_interface.RvClickView

class Craft2Adapter(private val context: Context, private val view : RvClickView) : RecyclerView.Adapter<Craft2Adapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemCraft2Binding
    private val dataList = ArrayList<Craft2>()

    class ViewHolder(binding : ItemCraft2Binding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val maker = binding.tvMaker
        val price = binding.tvPrice
        val layoutMain = binding.root
        var craftName = binding.tvCraft
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCraft2Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].imageUrl).into(holder.image)
        holder.maker.text = dataList[position].artist
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        holder.layoutMain.setOnClickListener {
            view.itemClick(dataList[position].productIdx)
        }
        holder.craftName.text = dataList[position].name
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<Craft2>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}