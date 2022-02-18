package com.example.songil.page_order

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.CraftAndAmount
import com.example.songil.databinding.OrderActivityBinding
import com.example.songil.recycler.adapter.Craft4Adapter
import com.example.songil.utils.changeToPriceForm
import com.example.songil.utils.changeToPriceFormKr
import com.example.songil.webview_address.WebAddressActivity
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser

// 여기 부트페이 테스트 api key 있다. commit 금지
class OrderActivity : BaseActivity<OrderActivityBinding>(R.layout.order_activity){

    private val orderViewModel : OrderViewModel by lazy { ViewModelProvider(this)[OrderViewModel::class.java] }
    private var fixEditTextByUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @SuppressWarnings("unchecked")
        val orderCrafts = intent.getSerializableExtra("ORDER_CRAFTS") as ArrayList<CraftAndAmount>

        binding.viewModel = orderViewModel
        binding.lifecycleOwner = this

        if (!GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)){
            binding.layoutBenefit.visibility =  View.GONE
            binding.spaceCoupon.visibility = View.GONE
        }

        BootpayAnalytics.init(this, "_")

        setRecyclerView()
        setObserver()
        setButton()
        setEditText()

        orderViewModel.tryGetOrderForm(orderCrafts)
    }

    private fun setButton(){
        val addressResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                val address = result.data?.getStringExtra("address")?.split(",") ?: arrayListOf()
                if (address.size == 2) {
                    binding.etZipCode.setText(address[0])
                    binding.etAddress.setText(address[1])
                    orderViewModel.tryCheckExtraFee()
                }
            }
        }

        binding.btnSearchAddress.setOnClickListener {
            addressResult.launch(Intent(this, WebAddressActivity::class.java))
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnPayment.setOnClickListener {
            goBootPayRequest()
        }
    }

    private fun setRecyclerView(){
        binding.rvCraft.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCraft.adapter = Craft4Adapter()
    }

    private fun setEditText(){
        binding.etPoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!fixEditTextByUser){
                    val disCount = s.toString().toIntOrNull()
                    if (disCount != null){
                        orderViewModel.priceData.applyUserPoint(disCount)
                        fixEditTextByUser = true
                        binding.etPoint.setText(orderViewModel.priceData.usePoint.toString())
                    } else {
                        orderViewModel.priceData.applyUserPoint(0)
                    }
                    applyPriceChange()
                } else {
                    fixEditTextByUser = false
                }
            }
        })

        val checkTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                orderViewModel.checkBtnActivate()
            }
        }

        binding.etAddress.addTextChangedListener(checkTextWatcher)
        binding.etPhoneNumber.addTextChangedListener(checkTextWatcher)
        binding.etRecipient.addTextChangedListener(checkTextWatcher)
        binding.etDetailAddress.addTextChangedListener(checkTextWatcher)
        binding.etZipCode.addTextChangedListener(checkTextWatcher)
    }

    private fun setObserver(){
        val getOrderIntoResult = Observer<Int>{ resultCode ->
            when (resultCode){
                200 -> {
                    (binding.rvCraft.adapter as Craft4Adapter).applyData(orderViewModel.craftList)
                    binding.tvAmountPoint.text = getString(R.string.form_have_point, changeToPriceForm(orderViewModel.priceData.havePoint))
                    applyPriceChange()
                }
                2301 -> { // when craft is not exist

                }
            }
        }
        orderViewModel.getOrderInfoResult.observe(this, getOrderIntoResult)

        val getExtraFeeResult = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    applyPriceChange()
                }
            }
        }
        orderViewModel.getExtraFeeResult.observe(this, getExtraFeeResult)
    }

    private fun applyPriceChange(){
        binding.tvSumValue.text = changeToPriceFormKr(orderViewModel.priceData.calTotalPrice())
        binding.tvCraftPricesValue.text = changeToPriceFormKr(orderViewModel.priceData.craftTotalPrice)
        binding.tvShippingFeeValue.text = changeToPriceFormKr(orderViewModel.priceData.shippingFee, isMinus = false)
        binding.tvPointDiscountValue.text = changeToPriceFormKr(orderViewModel.priceData.usePoint, isMinus = true)
        binding.tvCouponDiscountValue.text = changeToPriceFormKr(orderViewModel.priceData.couponDiscount, isMinus = true)
        if (orderViewModel.priceData.extraShippingFee != 0) {
            binding.tvExtraShippingFee.visibility = View.VISIBLE
            binding.tvExtraShippingFeeValue.visibility = View.VISIBLE
            binding.tvExtraShippingFeeValue.text = changeToPriceFormKr(orderViewModel.priceData.extraShippingFee, isMinus = false)
        } else {
            binding.tvExtraShippingFee.visibility = View.GONE
            binding.tvExtraShippingFeeValue.visibility = View.GONE
        }
        binding.btnPayment.text = getString(R.string.form_payment_string, changeToPriceForm(orderViewModel.priceData.calTotalPrice()))
    }

    private fun goBootPayRequest() {
        val bootUser = BootUser().setPhone("010-7748-8084")
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))
        Bootpay.init(this).setApplicationId("_").setContext(this)
            .setBootUser(bootUser).setBootExtra(bootExtra).setUX(UX.PG_DIALOG).setPG(PG.KCP).setMethod(Method.CARD)
            .setName("테스트 상품명").setOrderId("1234").setPrice(1000).onDone { message ->
                Log.d("done", message)
            }
            .onReady { message ->
                Log.d("ready", message)
            }
            .onCancel { message ->
                Log.d("cancel", message)
            }
            .onError{ message ->
                Log.d("error", message)
            }
            .onClose { _ ->
                Log.d("close", "close")
            }
            .request()
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}