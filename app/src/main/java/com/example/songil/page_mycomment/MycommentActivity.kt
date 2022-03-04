package com.example.songil.page_mycomment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MycommentActivityBinding
import com.example.songil.page_commentwrite.CommentWriteActivity
import com.example.songil.popup_remove.RemoveDialog
import com.example.songil.popup_remove.popup_interface.PopupRemoveView
import com.example.songil.recycler.adapter.CraftCommentPagingAdapter
import com.example.songil.recycler.adapter.MyWritableCommentPagingAdapter
import com.example.songil.recycler.decoration.Top16Decoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MycommentActivity : BaseActivity<MycommentActivityBinding>(R.layout.mycomment_activity), PopupRemoveView {

    private lateinit var viewModel : MycommentViewModel
    private val writeCommentActivityResult : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            (binding.rvContentWritableComment.adapter as MyWritableCommentPagingAdapter).changeItem(updatePosition)
        }
    }
    private var updatePosition = 0
    private var writablePagingJob : Job ?= null
    private var writtenPagingJob : Job ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MycommentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setRecyclerView()
        setObserver()

        binding.layoutRefreshWrittenComment.setOnRefreshListener {
            (binding.rvContentWrittenComment.adapter as CraftCommentPagingAdapter).refresh()
            binding.layoutRefreshWrittenComment.isRefreshing = false
        }

        binding.layoutRefreshWritableComment.setOnRefreshListener {
            (binding.rvContentWritableComment.adapter as MyWritableCommentPagingAdapter).refresh()
            binding.layoutRefreshWritableComment.isRefreshing = false
        }

        viewModel.changeCategory(0)
    }

    private fun setObserver(){
        // 0은 작성 가능한 코멘트 화면, 1은 작성한 코멘트 화면
        // 화면을 전환할 때마다 empty view 는 숨김처리
        val fragmentIdxObserver = Observer<Int>{ liveData ->
            binding.viewEmpty.root.visibility = View.GONE
            when (liveData){
                0 -> {
                    binding.layoutRefreshWritableComment.visibility = View.VISIBLE
                    binding.layoutRefreshWrittenComment.visibility = View.GONE
                    viewModel.tryGetWritableCommentCnt()
                }
                else -> {
                    binding.layoutRefreshWritableComment.visibility = View.GONE
                    binding.layoutRefreshWrittenComment.visibility = View.VISIBLE
                    viewModel.tryGetWrittenCommentCnt()
                }
            }
        }
        viewModel.currentFragmentIdx.observe(this, fragmentIdxObserver)

        // 작성 가능한 코멘트의 개수가 0인 경우, 작성 가능한 코멘트가 없다는걸 나타내는 view 표시
        val writableCommentIsEmptyObserver = Observer<Boolean> { isEmpty ->
            if (isEmpty){
                binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_writable_comment)
                binding.viewEmpty.root.visibility = View.VISIBLE
            } else {
                loadWritableComment()
            }
        }
        viewModel.writableCommentIsEmpty.observe(this, writableCommentIsEmptyObserver)

        // 작성한 코멘트의 개수가 0인 경우, 작성한 코멘트가 없다는걸 나타내는 view 표시
        val writtenCommentIsEmptyObserver = Observer<Boolean> { isEmpty ->
            if (isEmpty) {
                binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_written_comment)
                binding.viewEmpty.root.visibility = View.VISIBLE
            } else {
                loadWrittenComment()
            }
        }
        viewModel.writtenCommentIsEmpty.observe(this, writtenCommentIsEmptyObserver)

        // 코멘트 삭제 api 호출 결과 observer
        val removeCommentResult = Observer<Int> { resultCode ->
            if (resultCode == 200) {
                (binding.rvContentWrittenComment.adapter as CraftCommentPagingAdapter).applyRemoveResult(viewModel.removeTargetCommentPosition)
            }
        }
        viewModel.deleteCommentResult.observe(this, removeCommentResult)
    }

    private fun setRecyclerView(){
        binding.rvContentWritableComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContentWritableComment.adapter = MyWritableCommentPagingAdapter(::goToWritePage)

        binding.rvContentWrittenComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContentWrittenComment.adapter = CraftCommentPagingAdapter(::removeComment)
        binding.rvContentWrittenComment.addItemDecoration(Top16Decoration(this))
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    // 작성한 코멘트 삭제버튼 클릭시 코멘트 삭제를 묻는 dialog 호출
    private fun removeComment(position : Int, commentIdx : Int){
        viewModel.removeTargetCommentIdx = commentIdx
        viewModel.removeTargetCommentPosition = position

        val dialog = RemoveDialog(this, specificText = getString(R.string.confirmation_remove_comment))
        dialog.show(supportFragmentManager, dialog.tag)
    }

    // MyWritableCommentPagingAdapter 에서 코멘트 작성 버튼 클릭시 호출
    private fun goToWritePage(position : Int, orderDetailIdx : Int){
        updatePosition = position
        val intent = Intent(this, CommentWriteActivity::class.java)
        intent.putExtra("ORDER_DETAIL_IDX", orderDetailIdx)
        writeCommentActivityResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    // 작성 가능한 코멘트에 대한 페이징 데이터 수신
    private fun loadWritableComment(){
        writablePagingJob?.cancel()
        writtenPagingJob?.cancel()
        writablePagingJob = lifecycleScope.launch {
            viewModel.writableCommentFlow.collectLatest { pagingData ->
                (binding.rvContentWritableComment.adapter as MyWritableCommentPagingAdapter).submitData(pagingData)
            }
        }
    }

    // 작성한 코멘트에 대한 페이징 데이터 수신
    private fun loadWrittenComment() {
        writablePagingJob?.cancel()
        writtenPagingJob?.cancel()
        writtenPagingJob = lifecycleScope.launch {
            viewModel.writtenCommentFlow.collectLatest { pagingData ->
                (binding.rvContentWrittenComment.adapter as CraftCommentPagingAdapter).submitData(pagingData)
            }
        }
    }

    // 코멘트 삭제 확인 dialog 에서 yes 를 누른 경우 호출되는 코멘트 삭제 부분
    override fun popupRemoveClick() {
        viewModel.tryDeleteComment()
    }
}