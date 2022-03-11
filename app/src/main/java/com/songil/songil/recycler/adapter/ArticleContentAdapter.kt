package com.songil.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.data.ArticleContentInfo
import com.songil.songil.databinding.*

class ArticleContentAdapter(private val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val text = 1
    private val image = 2
    //private val product = 3 // else 로 사용
    private val dataList = ArrayList<ArticleContentInfo>()

    class TextViewHolder(binding : ItemArticleContentTextBinding) : RecyclerView.ViewHolder(binding.root){
        val text = binding.tvContent
    }

    class ImageViewHolder(binding : ItemArticleContentImageBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivContent
    }

    class ProductViewHolder(binding : ItemArticleContentCraftBinding) : RecyclerView.ViewHolder(binding.root){
        val productName = binding.tvProductName
        val artistName = binding.tvArtistName
        val productImage = binding.ivProduct
        val price = binding.tvPrice
        val line = binding.lineProduct
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            text -> {
                val binding = ItemArticleContentTextBinding.inflate(inflater, parent, false)
                TextViewHolder(binding)
            }
            image -> {
                val binding = ItemArticleContentImageBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }
            else -> {
                val binding = ItemArticleContentCraftBinding.inflate(inflater, parent, false)
                ProductViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            text -> {
                if (dataList[position].textData != null)
                    (holder as TextViewHolder).text.text = dataList[position].textData!!
            }
            image -> {
                if (dataList[position].imageData != null)
                    Glide.with(context).load(dataList[position].imageData).into((holder as ImageViewHolder).image)
            }
            else -> {
                if (dataList[position].craftData != null){
                    (holder as ProductViewHolder).artistName.text = dataList[position].craftData!!.artistName
                    holder.price.text = context.getString(R.string.form_price_won, dataList[position].craftData!!.price)
                    holder.productName.text = dataList[position].craftData!!.name
                    Glide.with(context).load(dataList[position].craftData!!.mainImageUrl).into(holder.productImage)
                    if (position == dataList.size - 1)
                        holder.line.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<ArticleContentInfo>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

}