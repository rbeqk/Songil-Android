package com.example.songil.page_with.with_abtest

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.data.ABTestViewInfo
import com.example.songil.databinding.SimpleRecyclerviewFragmentBinding
import com.example.songil.page_with.WithSubFragmentInterface
import com.example.songil.recycler.adapter.WithABTestAdapter

class WithFragmentAbtest : BaseFragment<SimpleRecyclerviewFragmentBinding>(SimpleRecyclerviewFragmentBinding::bind, R.layout.simple_recyclerview_fragment), WithSubFragmentInterface {

    private val viewModel : WithAbtestViewModel by lazy { ViewModelProvider(this)[WithAbtestViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setRecyclerView()

        /*lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as ABTestAdapter).submitData(pagingData)
            }
        }*/
        viewModel.getPageSize()
        viewModel.tryGetABTestData()
    }

    private fun setObserver(){
        val abTestResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvContent.adapter as WithABTestAdapter).updateList(viewModel.abTestData)
                }
            }
        }
        viewModel.loadAbTestResult.observe(viewLifecycleOwner, abTestResult)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = WithABTestAdapter()//ABTestAdapter(AbTestComparator)

        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvContent.layoutManager as LinearLayoutManager
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisible >= layoutManager.itemCount - 1){
                    viewModel.tryGetABTestData()
                }
            }
        })
    }

    object AbTestComparator : DiffUtil.ItemCallback<ABTestViewInfo>(){
        override fun areItemsTheSame(oldItem: ABTestViewInfo, newItem: ABTestViewInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ABTestViewInfo, newItem: ABTestViewInfo): Boolean {
            return (oldItem.abTest.voteInfo == newItem.abTest.voteInfo) && (oldItem.abTest.finishInfo == newItem.abTest.finishInfo)
        }

    }

    override fun onShow() {
        binding.rvContent.scrollToPosition(0)
    }

    override fun sort(sort: String) {

    }

}