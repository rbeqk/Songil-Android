package com.songil.songil.page_with.with_story

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.FrontStory
import com.songil.songil.databinding.SimpleRecyclerviewFragmentSwipeBinding
import com.songil.songil.page_main.MainActivity
import com.songil.songil.page_story.StoryActivity
import com.songil.songil.page_with.WithSubFragmentInterface
import com.songil.songil.recycler.adapter.WithStoryAdapter
import com.songil.songil.recycler.decoration.WithStoryDecoration
import com.songil.songil.recycler.rv_interface.RvSingleUpdateView
import com.songil.songil.utils.dpToPx
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentStory : BaseFragment<SimpleRecyclerviewFragmentSwipeBinding>(SimpleRecyclerviewFragmentSwipeBinding::bind, R.layout.simple_recyclerview_fragment_swipe), WithSubFragmentInterface, RvSingleUpdateView {

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
        (binding.rvContent.adapter as WithStoryAdapter).attachSingleUpdateView(this)
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
        //viewModel.pointer?.invalidate()//(binding.rvContent.adapter as WithStoryAdapter).refresh()
    }

    override var recentTargetPosition: Int = 0
    override val singleItemUpdateCallback: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            result.data?.let {
                val item = it.getSerializableExtra("STORY")
                if (item == null){
                    (binding.rvContent.adapter as WithStoryAdapter).refresh()
                } else {
                    (binding.rvContent.adapter as WithStoryAdapter).applySingleItemChange(recentTargetPosition, item)
                }
            }
        }
    }

    override fun targetItemClick(position: Int, targetIdx: Int) {
        recentTargetPosition = position
        val intent = Intent(context, StoryActivity::class.java)
        intent.putExtra(GlobalApplication.STORY_IDX, targetIdx)
        singleItemUpdateCallback.launch(intent)
        (activity as BaseActivity<*>).overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}