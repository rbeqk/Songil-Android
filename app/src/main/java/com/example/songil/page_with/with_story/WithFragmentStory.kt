package com.example.songil.page_with.with_story

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.data.FrontStory
import com.example.songil.databinding.SimpleRecyclerviewFragmentBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.recycler.adapter.RvFrontStoryAdapter
import com.example.songil.recycler.decoration.RvItemStoryDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentStory : BaseFragment<SimpleRecyclerviewFragmentBinding>(SimpleRecyclerviewFragmentBinding::bind, R.layout.simple_recyclerview_fragment) {

    private lateinit var viewModel: WithStoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WithStoryViewModel::class.java]

        setRecyclerView()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as RvFrontStoryAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(context, 2)
        binding.rvContent.adapter = RvFrontStoryAdapter(WithStoryComparator)
        binding.rvContent.addItemDecoration(RvItemStoryDecoration(context as MainActivity))
    }

    object WithStoryComparator : DiffUtil.ItemCallback<FrontStory>(){
        override fun areItemsTheSame(oldItem: FrontStory, newItem: FrontStory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FrontStory, newItem: FrontStory): Boolean {
            return (oldItem.isLike == newItem.isLike) && (oldItem.likeCount == newItem.likeCount)
        }

    }
}