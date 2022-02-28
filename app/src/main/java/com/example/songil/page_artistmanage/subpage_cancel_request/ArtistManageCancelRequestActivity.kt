package com.example.songil.page_artistmanage.subpage_cancel_request

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.CancelOrReturn
import com.example.songil.data.ChangedItemFromAPI
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.popup_Yes_Or_No.CancelOrReturnDialog
import com.example.songil.recycler.adapter.CancelRequestListPagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArtistManageCancelRequestActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : ArtistManageCancelRequestViewModel by lazy { ViewModelProvider(this)[ArtistManageCancelRequestViewModel::class.java] }
    private var pagingJob : Job ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setButton()
        setObserver()

        viewModel.tryGetPageCnt()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

        binding.tvTitle.text = getString(R.string.cancel_return_request_list)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_cancel_request)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = CancelRequestListPagingAdapter(::callDialog)
    }

    private fun callDialog(cancelOrReturn: CancelOrReturn, isApprove : Boolean, orderDetailIdx : Int, positionInAdapter : Int){
        val title = when(cancelOrReturn){
            CancelOrReturn.CANCEL -> {
                if (isApprove) { "주문 취소 요청을 승인하시겠습니까?" }
                else {"주문 취소 요청을 거절하시겠습니까?"}
            }
            CancelOrReturn.RETURN -> {
                if (isApprove) { "반품 요청을 승인하시겠습니까?" }
                else {"반품 요청을 거절하시겠습니까?"}
            }
        }
        val dialog = CancelOrReturnDialog(title = title, cancelOrReturn = cancelOrReturn, isApprove = isApprove, orderDetailIdx = orderDetailIdx, position = positionInAdapter, ::callAnswerRequestApi)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun callAnswerRequestApi(cancelOrReturn: CancelOrReturn, isApprove : Boolean, orderDetailIdx : Int, positionInAdapter : Int){
        viewModel.trySendRequestAnswer(cancelOrReturn, isApprove, orderDetailIdx, positionInAdapter)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver(){
        val pageCntObserver = Observer<Int>{ resultCode ->
            binding.layoutRefresh.isRefreshing = false
            when (resultCode){
                200 -> {
                    if (viewModel.startPage == 0){
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    }
                    else {
                        binding.viewEmpty.root.visibility = View.GONE
                        restartJob()
                    }
                }
            }
        }
        viewModel.pageCntResult.observe(this, pageCntObserver)

        val changeItemObserver = Observer<ChangedItemFromAPI<Int>> { liveData ->
            when (liveData.ApiResultCode){
                200 -> {
                    (binding.rvContent.adapter as CancelRequestListPagingAdapter).applyChange(liveData.newData?: 0, liveData.position)
                }
                4001 -> { // 부트페이 api 에서 취소 불가가 발생한 경우 (취소 불가, 해당거래 취소 실패 등)
                    (binding.rvContent.adapter as CancelRequestListPagingAdapter).applyChange(liveData.newData?: 0, liveData.position)
                    showSimpleToastMessage(viewModel.errorMsg)
                }
            }
        }
        viewModel.requestAnswerResult.observe(this, changeItemObserver)
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as CancelRequestListPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}