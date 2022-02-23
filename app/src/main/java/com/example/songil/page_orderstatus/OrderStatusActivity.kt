package com.example.songil.page_orderstatus

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.recycler.adapter.OrdersPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderStatusActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : OrderStatusViewModel by lazy { ViewModelProvider(this)[OrderStatusViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.order_status)

        setRecyclerView()
        setObserver()
        setButton()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as OrdersPagingAdapter).submitData(pagingData)
            }
        }

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvContent.adapter as OrdersPagingAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = OrdersPagingAdapter(this)
    }

    private fun setObserver(){

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