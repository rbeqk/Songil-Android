package com.example.songil.page_articlecontent

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ArticleActivityContentBinding
import com.example.songil.recycler.adapter.RvArticleContentAdapter
import com.example.songil.recycler.decoration.RvArticleContentDecoration

class ArticleContentActivity : BaseActivity<ArticleActivityContentBinding>(R.layout.article_activity_content) {

    private lateinit var viewModel: ArticleContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ArticleContentViewModel::class.java]

        setRecyclerView()
        setObserver()

        viewModel.tempGetArticle()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = RvArticleContentAdapter(this)
        binding.rvContent.addItemDecoration(RvArticleContentDecoration(this))
    }

    private fun setObserver(){
        val articleObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvContent.adapter as RvArticleContentAdapter).applyData(viewModel.articleData.content)
                    setView()
                }
                else -> {

                }
            }
        }
        viewModel.getArticleResult.observe(this, articleObserver)
    }

    private fun setView(){
        Glide.with(this).load(viewModel.articleData.mainImageUrl).into(binding.ivThumbnail)
        binding.tvArticleTitle.text = viewModel.articleData.title
        binding.tvEditorName.text = viewModel.articleData.editorName
        binding.tvUploadDate.text = viewModel.articleData.createdAt
        binding.tvCategory.text = "인터뷰"
    }
}