package com.example.songil.viewPager2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.data.SimpleArticle
import com.example.songil.databinding.ItemArticleTitleVp2Binding
import com.example.songil.page_articlecontent.ArticleContentActivity

class Vp2ArticleTitleAdapter(private val context: Context) : RecyclerView.Adapter<Vp2ArticleTitleAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemArticleTitleVp2Binding
    private val articleList = ArrayList<SimpleArticle>()

    class ViewHolder(binding : ItemArticleTitleVp2Binding) : RecyclerView.ViewHolder(binding.root){
        val thumbnail = binding.ivThumbnail
        val articleTitle = binding.tvArticleTitle
        val articleEditor = binding.tvArticleEditor
        val articleType = binding.tvArticleType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemArticleTitleVp2Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.articleEditor.text = articleList[position].editorName
        holder.articleTitle.text = articleList[position].articleTitle
        Glide.with(context).load(articleList[position].articleThumbNail).into(holder.thumbnail)
        when (articleList[position].articleType){
            "magazine" -> holder.articleType.text = "매거진"
            "interview"-> holder.articleType.text = "인터뷰"
            "special" -> holder.articleType.text = "기획전"
            else -> holder.articleType.text = "아티클"
        }
        //holder.articleType.text = articleList[position].articleType
        holder.thumbnail.setOnClickListener {
            context.startActivity(Intent(context, ArticleContentActivity::class.java))
        }
    }

    override fun getItemCount(): Int = articleList.size

    fun applyData(inputData : ArrayList<SimpleArticle>){
        articleList.clear()
        articleList.addAll(inputData)
        notifyDataSetChanged()
    }
}