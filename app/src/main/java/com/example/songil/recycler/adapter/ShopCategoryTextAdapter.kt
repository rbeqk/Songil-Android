package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ItemShopCategoryTextBinding
import com.example.songil.recycler.rv_interface.RvCategoryView

class ShopCategoryTextAdapter(private val context : Context, private val view : RvCategoryView<Int>) : RecyclerView.Adapter<ShopCategoryTextAdapter.ViewHolder>() {

    private var currentCategory = ""
    private val dataList = GlobalApplication.categoryList
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemShopCategoryTextBinding

    class ViewHolder(binding : ItemShopCategoryTextBinding) : RecyclerView.ViewHolder(binding.root){
        val categoryName = binding.tvbtnSelectCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemShopCategoryTextBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryName.text = dataList[position].category
        if (dataList[position].category == currentCategory){
            holder.categoryName.setBackgroundColor(ContextCompat.getColor(context, R.color.songil_2))
            holder.categoryName.setTextColor(ContextCompat.getColor(context, R.color.songil_1))
        } else {
            holder.categoryName.setBackgroundColor(ContextCompat.getColor(context, R.color.songil_1))
            holder.categoryName.setTextColor(ContextCompat.getColor(context, R.color.songil_2))
        }
        holder.categoryName.setOnClickListener {
            view.categoryClick(dataList[position].categoryIdx)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun changeCurrentCategory(newCategory : String) {
        currentCategory = newCategory
        notifyDataSetChanged()
    }
}