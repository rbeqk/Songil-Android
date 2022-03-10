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

    // 주문취소 혹은 반품요청에 대한 승인 or 거부를 눌렀을 때 확인하는 popup dialog 호출
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
        // 각 인자를 넘겨주고, callAnswerRequestApi 함수에서 넘겨준 4개의 인자를 사용한다.
        val dialog = CancelOrReturnDialog(title = title, cancelOrReturn = cancelOrReturn, isApprove = isApprove, orderDetailIdx = orderDetailIdx, position = positionInAdapter, ::callAnswerRequestApi)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    // dialog 에 넘겨주기 위한 함수
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

        // 반품 및 주문취소 승인/거부를 호출한 다음 결과 반영
        // 만약 api 가 성공적으로 수행되었다면, recyclerView 에 있는 클릭한 item 을 갱신한다.
        val changeItemObserver = Observer<ChangedItemFromAPI<Int>> { liveData ->
            when (liveData.ApiResultCode){
                200 -> { // 성공하면 item 갱신
                    (binding.rvContent.adapter as CancelRequestListPagingAdapter).applyChange(liveData.newData?: 0, liveData.position)
                }
                4001 -> { // 부트페이 api 에서 취소 불가가 발생한 경우 (취소 불가, 해당거래 취소 실패 등)
                    (binding.rvContent.adapter as CancelRequestListPagingAdapter).applyChange(liveData.newData?: 0, liveData.position)
                    //showSimpleToastMessage(viewModel.errorMsg)
                }
            }
        }
        viewModel.requestAnswerResult.observe(this, changeItemObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
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