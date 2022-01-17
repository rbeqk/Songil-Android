package com.example.songil.page_with.with_abtest

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SimpleRecyclerviewFragmentSwipeBinding
import com.example.songil.page_with.WithSubFragmentInterface
import com.example.songil.recycler.adapter.WithABTestPagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentAbtest : BaseFragment<SimpleRecyclerviewFragmentSwipeBinding>(SimpleRecyclerviewFragmentSwipeBinding::bind, R.layout.simple_recyclerview_fragment_swipe), WithSubFragmentInterface {

    private val viewModel : WithAbtestViewModel by lazy { ViewModelProvider(this)[WithAbtestViewModel::class.java] }
    private var pagingJob : Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setRecyclerView()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

        viewModel.tryGetPageCnt()
    }

    private fun setObserver(){
        val pageCntResult = Observer<Int>{ _ ->
            binding.layoutRefresh.isRefreshing = false
            viewModel.isRefresh = true
            initAndLoad()
            (binding.rvContent.adapter as WithABTestPagingAdapter).refresh()
        }
        viewModel.startIdx.observe(viewLifecycleOwner, pageCntResult)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = WithABTestPagingAdapter()
    }

    private fun initAndLoad(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as WithABTestPagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as WithABTestPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun onShow() {
        binding.rvContent.scrollToPosition(0)
    }

    override fun sort(sort: String) {
        viewModel.sort = sort
        viewModel.tryGetPageCnt()
    }

    override fun getSort(): String = viewModel.sort

}