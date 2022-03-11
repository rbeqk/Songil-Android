package com.songil.songil.page_articlecontent

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.ArticleActivityContentBinding
import com.songil.songil.page_main.MainActivity
import com.songil.songil.recycler.adapter.ArticleContentAdapter
import com.songil.songil.recycler.adapter.ArticleRelatedAdapter
import com.songil.songil.recycler.decoration.ArticleContentDecoration
import com.google.android.material.chip.Chip

class ArticleContentActivity : BaseActivity<ArticleActivityContentBinding>(R.layout.article_activity_content) {

    private lateinit var viewModel: ArticleContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val articleIdx = intent.getIntExtra(GlobalApplication.ARTICLE_IDX, -1)

        viewModel = ViewModelProvider(this)[ArticleContentViewModel::class.java]

        setRecyclerView()
        setObserver()
        setButton()

        viewModel.tryGetArticleContent(articleIdx)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = ArticleContentAdapter(this)
        binding.rvContent.addItemDecoration(ArticleContentDecoration(this))

        binding.rvRelatedArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRelatedArticle.adapter = ArticleRelatedAdapter()
    }

    private fun setObserver(){
        val articleObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvContent.adapter as ArticleContentAdapter).applyData(viewModel.articleData.content)
                    setArticleInfo()
                }
            }
        }
        viewModel.getArticleResult.observe(this, articleObserver)

        val likeObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    setLikeInfo()
                }
            }
        }
        viewModel.favButtonResultCode.observe(this, likeObserver)

        val errorObserver = Observer<BaseViewModel.FetchState>{ fetchState ->
            binding.btnShare.isClickable = false
            when (fetchState){
                BaseViewModel.FetchState.BAD_INTERNET -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.FAIL -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.WRONG_CONNECTION -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.PARSE_ERROR -> {}
                null -> {}
            }
        }
        viewModel.fetchState.observe(this, errorObserver)
    }

    private fun setArticleInfo(){
        Glide.with(this).load(viewModel.articleData.mainImageUrl).into(binding.ivThumbnail)
        binding.tvArticleTitle.text = viewModel.articleData.title
        binding.tvEditorName.text = viewModel.articleData.editorName
        binding.tvUploadDate.text = viewModel.articleData.createdAt
        when(viewModel.articleData.articleCategoryIdx){
            1 -> binding.tvCategory.text = "인터뷰"
            2 -> binding.tvCategory.text = "매거진"
            else -> binding.tvCategory.text = "아티클"
        }
        (binding.rvRelatedArticle.adapter as ArticleRelatedAdapter).applyData(viewModel.articleData.relatedArticle)
        setTag(viewModel.articleData.tag)
        setLikeInfo()
    }

    private fun setTag(tagList : ArrayList<String>){
        for (tag in tagList){
            val chip = Chip(this)
            chip.text = tag
            chip.setChipBackgroundColorResource(R.color.g_1)
            chip.setTextColor(ContextCompat.getColor(this, R.color.songil_2))
            chip.isCheckable = false
            chip.setOnClickListener {

            }
            binding.cgTag.addView(chip)
        }
    }

    private fun setLikeInfo(){
        binding.tvFavoriteCount.text = viewModel.articleData.totalLikeCnt.toString()
        binding.btnFavorite.setBackgroundResource(if (viewModel.articleData.isLike == "Y") R.drawable.ic_heart_base_16 else R.drawable.ic_heart_line_16)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        binding.btnFavorite.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                viewModel.tryChangeLikeData()
            } else {
                callNeedLoginDialog()
            }
        }

        binding.btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, viewModel.shareMessage())
            val shareIntent = Intent.createChooser(intent, "share")
            startActivity(shareIntent)
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}