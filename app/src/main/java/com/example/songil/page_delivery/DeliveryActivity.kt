package com.example.songil.page_delivery

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.DeliveryActivityBinding
import com.example.songil.popup_warning.WarningDialog
import com.example.songil.recycler.adapter.DeliveryAdapter

class DeliveryActivity : BaseActivity<DeliveryActivityBinding>(R.layout.delivery_activity) {

    private val viewModel : DeliveryViewModel by lazy { ViewModelProvider(this, DeliveryViewModel.DeliveryViewModelFactory(intent.getIntExtra("ORDER_DETAIL_IDX", -1)))[DeliveryViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        setRecyclerView()
        setButton()
        setObserver()

        binding.layoutMainScroll.run {
            header = binding.layoutColumn
        }

        viewModel.tryGetTrackingData()
    }

    private fun setObserver(){
        val tempResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvContent.adapter as DeliveryAdapter).applyData(viewModel.dataList)
                    if (GlobalApplication.courierMap[viewModel.tCode] != null){
                        binding.tvCourierCompanyValue.text = GlobalApplication.courierMap[viewModel.tCode]
                    } else {
                        binding.tvCourierCompanyValue.text = "등록되지 않은 택배사"
                    }
                    binding.tvWaybillNumberValue.text = viewModel.tInvoice
                }
                2340 -> {
                    showSimpleToastMessage("존재하지 않는 주문입니다.")
                    finish()
                }
                2400 -> {
                    showSimpleToastMessage("권한이 없습니다.")
                    finish()
                }
                3000 -> {
                    showSimpleToastMessage("자동로그인 혹은 로그인을 수행한 이후 30일이 경과되어 로그아웃되었습니다.")
                    finish()
                }
                3401 -> {
                    val dialog = WarningDialog("입력된 배송정보가 없습니다.", "아직 배송정보가 입력되지 않아\n배송 정보를 조회할 수 없습니다.", ::finish)
                    dialog.show(supportFragmentManager, dialog.tag)
                }
            }
        }
        viewModel.deliveryTrackingResult.observe(this, tempResult)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvbtnCopy.setOnClickListener {
            val clipManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("WAYBILL", binding.tvWaybillNumberValue.text)
            clipManager.setPrimaryClip(clipData)
            showSimpleToastMessage("운송장번호가 복사되었습니다.")
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = DeliveryAdapter()
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}