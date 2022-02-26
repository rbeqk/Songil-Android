package com.example.songil.page_artistmanage.subpage_cancel_request

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.recycler.adapter.CancelRequestListPagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArtistManageCancelRequestActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : ArtistManageCancelRequestViewModel by lazy { ViewModelProvider(this)[ArtistManageCancelRequestViewModel::class.java] }
    private var pagingJob : Job ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setButton()
        setObserver()

        viewModel.tryGetPageCnt()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

        binding.tvTitle.text = getString(R.string.cancel_return_request_list)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_cancel_request)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = CancelRequestListPagingAdapter()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver(){
        val pageCntObserver = Observer<Int>{ resultCode ->
            binding.layoutRefresh.isRefreshing = false
            when (resultCode){
                200 -> {
                    if (viewModel.startPage == 0){
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    }
                    else {
                        binding.viewEmpty.root.visibility = View.GONE
                        restartJob()
                    }
                }
            }
        }
        viewModel.pageCntResult.observe(this, pageCntObserver)
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as CancelRequestListPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}