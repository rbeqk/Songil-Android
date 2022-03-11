package com.songil.songil.viewPager2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.ItemMainArticleBinding
import com.songil.songil.page_articlecontent.ArticleContentActivity
import com.songil.songil.page_home.models.HomeArticle

class Vp2MainArticleAdapter(private val context : Context) : RecyclerView.Adapter<Vp2MainArticleAdapter.ViewHolder>()  {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainArticleBinding
    private val dataList = ArrayList<HomeArticle>()

    class ViewHolder(binding : ItemMainArticleBinding) : RecyclerView.ViewHolder(binding.root){
        val img = binding.ivThumbnail
        val title = binding.tvTitle
        val subTitle = binding.tvSubTitle
        val type = binding.tvArticleType
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataList[position].title
        holder.subTitle.text = dataList[position].summary
        when (dataList[position].categoryIdx){
            1-> holder.type.text = "인터뷰"
            2 -> holder.type.text = "매거진"
            3 -> holder.type.text = "기획전"
            else -> holder.type.text = "아티클"
        }
        Glide.with(context).load(dataList[position].mainImageUrl).into(holder.img)
        holder.root.setOnClickListener {
            val intent = Intent(context, ArticleContentActivity::class.java)
            intent.putExtra(GlobalApplication.ARTICLE_IDX, dataList[position].articleIdx)
            (context as BaseActivity<*>).startActivityHorizontal(intent)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<HomeArticle>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}