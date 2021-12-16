package com.example.songil.viewPager2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.data.SimpleArticle
import com.example.songil.databinding.ItemMainArticleBinding

class Vp2MainArticleAdapter(private val context : Context) : RecyclerView.Adapter<Vp2MainArticleAdapter.ViewHolder>()  {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainArticleBinding
    private val dataList = ArrayList<SimpleArticle>()

    class ViewHolder(binding : ItemMainArticleBinding) : RecyclerView.ViewHolder(binding.root){
        val img = binding.ivThumbnail
        val title = binding.tvTitle
        val subTitle = binding.tvSubTitle
        val type = binding.tvArticleType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataList[position].articleTitle
        holder.subTitle.text = dataList[position].editorName
        when (dataList[position].articleType){
            "magazine" -> holder.type.text = "매거진"
            "interview"-> holder.type.text = "인터뷰"
            "special" -> holder.type.text = "기획전"
            else -> holder.type.text = "아티클"
        }
        Glide.with(context).load(dataList[position].articleThumbNail).into(holder.img)
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<SimpleArticle>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}