package com.example.songil.page_artistmanage.subpage_orderstat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.recycler.adapter.OrdersArtistPagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArtistManageOrderStatActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity){
    private val viewModel : ArtistManageOrderStatViewModel by lazy { ViewModelProvider(this)[ArtistManageOrderStatViewModel::class.java] }
    private var pagingjob : Job?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setObserver()
        setRecyclerView()
        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

        binding.tvTitle.text = getString(R.string.order_status)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_artist_order_status)

        viewModel.tryGetPageCnt()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = OrdersArtistPagingAdapter()
    }

    private fun setObserver(){
        val pageCntObserver = Observer<Int> { resultCode ->
            binding.layoutRefresh.isRefreshing = false
            when (resultCode){
                200 -> {
                    if (viewModel.startPage == 0){
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    } else {
                        binding.viewEmpty.root.visibility = View.GONE
                        restartJob()
                    }
                }
            }
        }
        viewModel.pageCntResult.observe(this, pageCntObserver)
    }

    private fun restartJob(){
        pagingjob?.cancel()
        pagingjob = lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as OrdersArtistPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}