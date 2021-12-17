package com.example.songil.page_craft.subpage_comment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.CraftFragmentReviewBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.recycler.adapter.CraftCommentAdapter
import com.example.songil.recycler.decoration.CraftCommentDecoration

class CraftFragmentComment() : BaseFragment<CraftFragmentReviewBinding>(CraftFragmentReviewBinding::bind, R.layout.craft_fragment_review) {

    private val viewModel : CraftCommentViewModel by lazy { ViewModelProvider(this)[CraftCommentViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserver()

        viewModel.setNextPage()
        viewModel.tryGetReview()
    }

    private fun setRecyclerView(){
        binding.rvReview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = CraftCommentAdapter(activity as CraftActivity)
        binding.rvReview.addItemDecoration(CraftCommentDecoration(activity as CraftActivity))
    }

    private fun setObserver(){
        val updateObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvReview.adapter as CraftCommentAdapter).updateData(viewModel.craftCommentList)
                }
            }
        }
        viewModel.updateResult.observe(viewLifecycleOwner, updateObserver)
    }

    fun updateComment(){
        viewModel.tryGetReview()
    }
}