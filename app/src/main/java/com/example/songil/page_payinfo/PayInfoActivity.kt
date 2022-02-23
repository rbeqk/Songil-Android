package com.example.songil.page_payinfo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.PayinfoActivityBinding
import com.example.songil.recycler.adapter.Craft4Adapter
import com.example.songil.utils.changeToPriceFormKr

class PayInfoActivity : BaseActivity<PayinfoActivityBinding>(R.layout.payinfo_activity) {

    private val viewModel : PayInfoViewModel by lazy { ViewModelProvider(this, PayInfoViewModel.PayInfoViewModelFactory(intent.getIntExtra("ORDER_DETAIL_IDX", -1)))[PayInfoViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setRecyclerView()
        setObserver()

        viewModel.tryGetOrderDetailInfo()
    }

    private fun setRecyclerView(){
        binding.rvCraft.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCraft.adapter = Craft4Adapter()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver(){
        val getOrderDetailInfoObserver = Observer<Int>{ resultCode ->
            when (resultCode){
                200 -> { // 성공
                    applyDataToView()
                }
                2340 -> { // 존재하지 않는 orderDetailIdx
                    showSimpleToastMessage("존재하지 않는 주문내역입니다.")
                    finish()
                }
                3000 -> { // jwt 토큰 기한 만료
                    showSimpleToastMessage("자동 로그인 또는 로그인 이후 30일이 경과되어 자동 로그아웃 되었습니다.")
                    finish()
                }
                else -> { // 사용자에게 알려도 의미가 없거나, 발생할 확률이 극도로 낮은 에러 (db 에러, jwt 토큰 미입력 등)
                    showSimpleToastMessage("알수없는 에러가 발생하여 이전 페이지로 돌아갑니다.")
                    finish()
                }
            }
        }
        viewModel.getOrderDetailResult.observe(this, getOrderDetailInfoObserver)
    }

    private fun applyDataToView(){
        (binding.rvCraft.adapter as Craft4Adapter).applyData(viewModel.craftList)
        binding.tvRecipient.text = viewModel.orderDetailInfo.recipient
        binding.tvPhoneNumber.text = viewModel.orderDetailInfo.phone
        binding.tvAddress.text = viewModel.orderDetailInfo.address
        viewModel.orderDetailInfo.memo?.let {
            binding.tvShippingMemo.text = it
        }
        binding.tvCraftPricesValue.text = changeToPriceFormKr(viewModel.orderDetailInfo.totalCraftPrice)
        binding.tvShippingFeeValue.text = changeToPriceFormKr(viewModel.orderDetailInfo.basicShippingFee)
        if (viewModel.orderDetailInfo.extraShippingFee != 0){
            binding.tvExtraShippingFee.visibility = View.VISIBLE
            binding.tvExtraShippingFeeValue.visibility = View.VISIBLE
            binding.tvExtraShippingFeeValue.text = changeToPriceFormKr(viewModel.orderDetailInfo.extraShippingFee, false)
        } else {
            binding.tvExtraShippingFee.visibility = View.GONE
            binding.tvExtraShippingFeeValue.visibility = View.GONE
        }
        binding.tvPointDiscountValue.text = changeToPriceFormKr(viewModel.orderDetailInfo.pointDiscount, true)
        binding.tvCouponDiscountValue.text = changeToPriceFormKr(viewModel.orderDetailInfo.benefitDiscount, true)
        binding.tvSumValue.text = changeToPriceFormKr(viewModel.orderDetailInfo.finalPrice)
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}