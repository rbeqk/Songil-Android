package com.example.songil.page_with.with_story

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.data.FrontStory
import com.example.songil.databinding.SimpleRecyclerviewFragmentSwipeBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.page_with.WithSubFragmentInterface
import com.example.songil.recycler.adapter.WithStoryAdapter
import com.example.songil.recycler.decoration.WithStoryDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentStory() : BaseFragment<SimpleRecyclerviewFragmentSwipeBinding>(SimpleRecyclerviewFragmentSwipeBinding::bind, R.layout.simple_recyclerview_fragment_swipe), WithSubFragmentInterface {

    private val viewModel: WithStoryViewModel by lazy { ViewModelProvider(this)[WithStoryViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserver()

        initAndLoad()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetStartIdx()
            binding.layoutRefresh.isRefreshing = false
        }

        viewModel.tryGetStartIdx()
    }

    private fun initAndLoad(){
        lifecycleScope.launch {
            (binding.rvContent.adapter as WithStoryAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as WithStoryAdapter).submitData(pagingData)
            }
        }
    }

    private fun setObserver(){
        val startIdxObserver = Observer<Int>{ _ ->
            viewModel.isRefresh = true
            (binding.rvContent.adapter as WithStoryAdapter).refresh()
        }
        viewModel.startIdx.observe(viewLifecycleOwner, startIdxObserver)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(context, 2)
        binding.rvContent.adapter = WithStoryAdapter(WithStoryComparator)
        binding.rvContent.addItemDecoration(WithStoryDecoration(context as MainActivity))
    }

    object WithStoryComparator : DiffUtil.ItemCallback<FrontStory>(){
        override fun areItemsTheSame(oldItem: FrontStory, newItem: FrontStory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FrontStory, newItem: FrontStory): Boolean {
            return (oldItem.isLike == newItem.isLike) && (oldItem.totalLikeCnt == newItem.totalLikeCnt)
        }
    }
    override fun onShow() {
        binding.rvContent.scrollToPosition(0)
    }

    override fun sort(sort: String) {
        viewModel.sort = sort
        viewModel.isRefresh = true
        (binding.rvContent.adapter as WithStoryAdapter).refresh()
        initAndLoad()
    }

    override fun getSort(): String = viewModel.sort
}