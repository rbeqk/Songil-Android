package com.example.songil.page_mypage_ask_history

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.recycler.adapter.MyAskPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageAskActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {

    private val viewModel : MyPageAskViewModel by lazy { ViewModelProvider(this)[MyPageAskViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvContent.adapter as MyAskPagingAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }

        setButton()
        setRecyclerView()

        binding.tvTitle.text = getString(R.string.one_by_one_ask)

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as MyAskPagingAdapter).submitData(pagingData)
            }
        }
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

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}