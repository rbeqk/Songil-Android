package com.example.songil.page_craft.subpage_comment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.CraftFragmentReviewBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.recycler.adapter.CraftCommentAdapter
import com.example.songil.recycler.decoration.CraftCommentDecoration
import com.example.songil.utils.dpToPx

class CraftFragmentComment() : BaseFragment<CraftFragmentReviewBinding>(CraftFragmentReviewBinding::bind, R.layout.craft_fragment_review) {

    private val viewModel : CraftCommentViewModel by lazy { ViewModelProvider(this)[CraftCommentViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.setCraftIdx((activity as CraftActivity).intent.getIntExtra(GlobalApplication.CRAFT_IDX, 1))

        setRecyclerView()
        setObserver()

        fitLayoutHeight()
    }

    private fun setRecyclerView(){
        binding.rvReview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = CraftCommentAdapter(activity as CraftActivity, viewModel.craftCommentList)
        binding.rvReview.addItemDecoration(CraftCommentDecoration(activity as CraftActivity))
    }

    private fun setObserver(){
        val isPhotoObserver = Observer<Boolean>{ _ ->
            viewModel.tryGetCommentsPageCnt()
        }
        viewModel.photoOnly.observe(viewLifecycleOwner, isPhotoObserver)

        val pageCntObserver = Observer<Int>{ liveData ->
            if (liveData > 0){
                (binding.rvReview.adapter as CraftCommentAdapter).clearData()
                if (liveData == 1) {
                    viewModel.tryGetComments()
                } else {
                    viewModel.tryGetCommentFirst()
                }
            }
        }
        viewModel.craftCommentPageCnt.observe(viewLifecycleOwner, pageCntObserver)

        val updateObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvReview.adapter as CraftCommentAdapter).updateData(viewModel.newDataCnt)
                }
            }
        }
        viewModel.updateResult.observe(viewLifecycleOwner, updateObserver)
    }

    fun updateComment(){
        viewModel.tryGetComments()
    }

    private fun fitLayoutHeight(){
        binding.layoutMain.minHeight = getWindowSize()[1] - (activity as CraftActivity).getToolbarHeight() - getStatusBarHeight() - dpToPx(requireContext(), 56)
    }
}