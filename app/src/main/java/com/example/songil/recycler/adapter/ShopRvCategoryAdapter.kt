package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ShopItemCategoryBinding
import com.example.songil.recycler.rv_interface.RvCategoryView

class ShopRvCategoryAdapter(context: Context, private val view : RvCategoryView<String>) : RecyclerView.Adapter<ShopRvCategoryAdapter.ViewHolder>() {
    private val dataList = GlobalApplication.categoryList
    private lateinit var binding: ShopItemCategoryBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class ViewHolder(binding : ShopItemCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.ivPhoto
        val categoryName = binding.tvCategory
        val layoutMain = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ShopItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryName.text = dataList[position].category
        holder.imageView.setImageResource(dataList[position].categoryIcon)
        holder.layoutMain.setOnClickListener{
            view.categoryClick(dataList[position].category)
        }
    }

    override fun getItemCount(): Int = dataList.size
}