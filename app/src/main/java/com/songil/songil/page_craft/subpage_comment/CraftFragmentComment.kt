package com.songil.songil.page_craft.subpage_comment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.CraftFragmentReviewBinding
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.recycler.adapter.CraftCommentAdapter
import com.songil.songil.recycler.decoration.CraftCommentDecoration
import com.songil.songil.utils.dpToPx

class CraftFragmentComment : BaseFragment<CraftFragmentReviewBinding>(CraftFragmentReviewBinding::bind, R.layout.craft_fragment_review) {

    private val viewModel : CraftCommentViewModel by lazy { ViewModelProvider(this)[CraftCommentViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.setCraftIdx((activity as CraftActivity).intent.getIntExtra(GlobalApplication.CRAFT_IDX, 1))

        setRecyclerView()
        setObserver()

        setEmptyText()

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
            (binding.rvReview.adapter as CraftCommentAdapter).clearData()
            if (liveData > 0){
                binding.viewEmpty.root.visibility = View.GONE
                if (liveData == 1) {
                    viewModel.tryGetComments()
                } else {
                    viewModel.tryGetCommentFirst()
                }
            } else {
                setEmptyText()
                binding.viewEmpty.root.visibility = View.VISIBLE
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

    // 빈 화면에서 표기될 text 설정, 포토 코멘트만 보기일 경우와 아닌 경우가 구분되어 있다.
    private fun setEmptyText(){
        binding.viewEmpty.tvEmptyTarget.text = if (viewModel.photoOnly.value == true) getString(R.string.empty_craft_comment_photo) else getString(R.string.empty_craft_comment)
    }
}