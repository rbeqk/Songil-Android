package com.songil.songil.page_myfavorite_article

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.MydetailActivityBinding
import com.songil.songil.recycler.adapter.ArticlePagingAdapter
import com.songil.songil.recycler.decoration.ArticleDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyFavoriteArticleActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {
    private val viewModel : MyFavoriteArticleViewModel by lazy { ViewModelProvider(this)[MyFavoriteArticleViewModel::class.java] }
    private var pagingJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setRecyclerView()
        setObserver()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryCheckArticleEmpty()
        }

        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_favorite_article)
        binding.tvTitle.text = getString(R.string.like_article)

        viewModel.tryCheckArticleEmpty()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.tryCheckArticleEmpty()
    }

    private fun setObserver(){
        val cntObserver = Observer<Int>{ liveData ->
            binding.layoutRefresh.isRefreshing = false
            if (liveData == 0){
                binding.viewEmpty.root.visibility = View.VISIBLE
                clearJob()
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                if ((binding.rvContent.adapter as ArticlePagingAdapter).itemCount > 0){
                    (binding.rvContent.adapter as ArticlePagingAdapter).refresh()
                } else {
                    restartJob()
                }
            }
        }
        viewModel.totalCnt.observe(this, cntObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = ArticlePagingAdapter()
        binding.rvContent.addItemDecoration(ArticleDecoration(this))
    }

    private fun clearJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as ArticlePagingAdapter).submitData(PagingData.empty())
        }
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as ArticlePagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as ArticlePagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}