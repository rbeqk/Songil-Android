package com.example.songil.page_with.with_qna

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.data.WithQna
import com.example.songil.databinding.SimpleRecyclerviewFragmentBinding
import com.example.songil.recycler.adapter.RvWithQnaAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentQna : BaseFragment<SimpleRecyclerviewFragmentBinding>(SimpleRecyclerviewFragmentBinding::bind, R.layout.simple_recyclerview_fragment){

    private lateinit var viewModel: WithQnaViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WithQnaViewModel::class.java]

        setRecyclerView()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as RvWithQnaAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = RvWithQnaAdapter(WithQnaComparator)
    }

    object WithQnaComparator : DiffUtil.ItemCallback<WithQna>(){
        override fun areItemsTheSame(oldItem: WithQna, newItem: WithQna): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WithQna, newItem: WithQna): Boolean {
            return (oldItem.isLike == newItem.isLike) && (oldItem.likeCount == newItem.likeCount)
        }

    }
}