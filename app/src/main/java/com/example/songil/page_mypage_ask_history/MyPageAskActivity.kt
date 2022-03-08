package com.example.songil.page_mypage_ask_history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.recycler.adapter.MyAskPagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageAskActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {

    private val viewModel : MyPageAskViewModel by lazy { ViewModelProvider(this)[MyPageAskViewModel::class.java] }
    private var pagingJob : Job ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryCheckAskListCntIsZero()
        }

        setButton()
        setRecyclerView()
        setObserver()

        binding.tvTitle.text = getString(R.string.one_by_one_ask)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_inquiry_list)

        viewModel.tryCheckAskListCntIsZero()
    }

    private fun setObserver(){
        val itemCntIsZeroObserver = Observer<Boolean>{ cntIsZero ->
            binding.layoutRefresh.isRefreshing = false
            if (cntIsZero){
                binding.viewEmpty.root.visibility = View.VISIBLE
                clearJob()
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                if ((binding.rvContent.adapter as MyAskPagingAdapter).itemCount > 0){
                    (binding.rvContent.adapter as MyAskPagingAdapter).refresh()
                } else {
                    restartJob()
                }
            }
        }
        viewModel.itemCntIsZero.observe(this, itemCntIsZeroObserver)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = MyAskPagingAdapter()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun clearJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as MyAskPagingAdapter).submitData(PagingData.empty())
        }
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as MyAskPagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as MyAskPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}