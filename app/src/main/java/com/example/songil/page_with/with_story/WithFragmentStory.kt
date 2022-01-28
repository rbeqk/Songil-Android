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
import com.example.songil.utils.dpToPx
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentStory() : BaseFragment<SimpleRecyclerviewFragmentSwipeBinding>(SimpleRecyclerviewFragmentSwipeBinding::bind, R.layout.simple_recyclerview_fragment_swipe), WithSubFragmentInterface {

    private val viewModel: WithStoryViewModel by lazy { ViewModelProvider(this)[WithStoryViewModel::class.java] }
    private var pagingJob : Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserver()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetStartIdx()
        }

        binding.rvContent.setPadding(dpToPx(requireContext(), 14), 0, dpToPx(requireContext(), 14), 0)

        viewModel.tryGetStartIdx()
    }

    private fun initAndLoad(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as WithStoryAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as WithStoryAdapter).submitData(pagingData)
            }
        }
    }

    private fun setObserver(){
        val startIdxObserver = Observer<Int>{ _ ->
            binding.layoutRefresh.isRefreshing = false
            viewModel.isRefresh = true
            initAndLoad()
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
        viewModel.tryGetStartIdx()
    }

    override fun getSort(): String = viewModel.sort

    override fun reload() {
        (binding.rvContent.adapter as WithStoryAdapter).refresh()
    }
}