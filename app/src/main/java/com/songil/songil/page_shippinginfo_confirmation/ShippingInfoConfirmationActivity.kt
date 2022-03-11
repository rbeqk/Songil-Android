package com.songil.songil.page_shippinginfo_confirmation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.ShippinginfoActivityBinding

class ShippingInfoConfirmationActivity : BaseActivity<ShippinginfoActivityBinding>(R.layout.shippinginfo_activity) {
    private val viewModel : ShippingInfoConfirmationViewModel by lazy {
        ViewModelProvider(this, ShippingInfoConfirmationViewModel.ShippingInfoConfirmationViewModelFactory(intent.getIntExtra("ORDER_DETAIL_IDX", -1)))[ShippingInfoConfirmationViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setObserver()

        viewModel.tryGetShippingInfo()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver(){
        val getShippingInfoObserver = Observer<Int> { resultCode ->
            when (resultCode) {
                200 -> {
                    binding.tvbtnCourierCompany.text = viewModel.courierCompany
                    binding.tvDate.text = viewModel.date
                    binding.etWaybillNumber.text = viewModel.waybillNumber
                }
                2340 -> { // 존재하지 않는 orderDetailIdx

                }
                2400 -> { // 권한 없음

                }
            }
        }
        viewModel.getShippingInfoResult.observe(this, getShippingInfoObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}