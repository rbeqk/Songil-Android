package com.songil.songil.page_order

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.BuildConfig
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.CraftAndAmount
import com.songil.songil.databinding.OrderActivityBinding
import com.songil.songil.page_main.MainActivity
import com.songil.songil.page_order.subpage_benefit.ApplyBenefitActivity
import com.songil.songil.page_ordercomplete.OrdercompleteActivity
import com.songil.songil.popup_warning.WarningDialog
import com.songil.songil.recycler.adapter.Craft4Adapter
import com.songil.songil.utils.changeToPriceForm
import com.songil.songil.utils.changeToPriceFormKr
import com.songil.songil.webview_address.WebAddressActivity
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import org.json.JSONObject

class OrderActivity : BaseActivity<OrderActivityBinding>(R.layout.order_activity){

    private val orderViewModel : OrderViewModel by lazy { ViewModelProvider(this)[OrderViewModel::class.java] }
    private var fixEditTextByUser = false
    private val benefitActivityRegister = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            val intentData = result.data?.getIntExtra("BENEFIT_IDX", -1)
            val benefitIdx = if (intentData == -1) null else intentData
            orderViewModel.tryApplyBenefit(benefitIdx)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        val orderCrafts = intent.getSerializableExtra("ORDER_CRAFTS") as ArrayList<CraftAndAmount>

        binding.viewModel = orderViewModel
        binding.lifecycleOwner = this

        if (!GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)){
            binding.layoutBenefit.visibility =  View.GONE
            binding.spaceCoupon.visibility = View.GONE
        }

        BootpayAnalytics.init(this, BuildConfig.BOOTPAY_KEY)

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
                    //orderViewModel.tryCheckExtraFee()
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
            orderViewModel.trySendOrderInfo()
        }

        binding.btnCheckCoupon.setOnClickListener {
            val intent = Intent(this, ApplyBenefitActivity::class.java)
            intent.putExtra("ORDER_IDX", orderViewModel.orderIdx)
            benefitActivityRegister.launch(intent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
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
                if (!fixEditTextByUser) {
                    val disCount = s.toString().toIntOrNull()
                    if (disCount != null) {
                        orderViewModel.priceData.applyUserPoint(disCount)
                        if (disCount > orderViewModel.priceData.havePoint) {
                            fixEditTextByUser = true
                            binding.etPoint.setText(orderViewModel.priceData.usePoint.toString())
                            binding.etPoint.setSelection(binding.etPoint.length())
                        }
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

        val phoneTextWatcher = object : PhoneNumberFormattingTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                orderViewModel.checkBtnActivate()
            }
        }

        val zipTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                orderViewModel.checkBtnActivate()
                if (s.toString().length == 5){
                    orderViewModel.tryCheckExtraFee()
                }
            }

        }

        binding.etAddress.addTextChangedListener(checkTextWatcher)
        binding.etPhoneNumber.addTextChangedListener(phoneTextWatcher)
        binding.etRecipient.addTextChangedListener(checkTextWatcher)
        binding.etDetailAddress.addTextChangedListener(checkTextWatcher)
        binding.etZipCode.addTextChangedListener(zipTextWatcher)
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
                    val dialog = WarningDialog("구매할 수 없는 물품이 있습니다", "현재 주문하려는 제품이\n삭제되거나 품절되었습니다."){
                        finish()
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
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

        val applyBenefitObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    applyPriceChange()
                }
            }
        }
        orderViewModel.applyBenefitResult.observe(this, applyBenefitObserver)

        // 12.5 api 결과
        val postOrderEtcInfoObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    goBootPayRequest()
                }
            }
        }
        orderViewModel.postOrderInfoResult.observe(this, postOrderEtcInfoObserver)

        val orderVerificationObserver = Observer<Int> { resultCode ->
            when(resultCode){
                200 -> {
                    startActivityHorizontal(Intent(this, OrdercompleteActivity::class.java))
                }
                2328 -> { // 존재하지 않는 receiptId
                    showSimpleToastMessage("삭제된 주문 정보입니다")
                }
                3000 -> { // jwt 토큰 기한 만료
                    showSimpleToastMessage("자동로그인 혹은 로그인을 수행한 이후 30일이 경과되어 로그아웃되었습니다.")
                }
                3500 -> { // 위조된 요청
                    showSimpleToastMessage("!!위조된 요청입니다!!")
                }
                else -> {

                }
            }
        }
        orderViewModel.postOrderVerificationResult.observe(this, orderVerificationObserver)
    }

    private fun applyPriceChange(){
        binding.tvSumValue.text = changeToPriceFormKr(orderViewModel.priceData.calTotalPrice())
        binding.tvCraftPricesValue.text = changeToPriceFormKr(orderViewModel.priceData.craftTotalPrice)
        binding.tvShippingFeeValue.text = changeToPriceFormKr(orderViewModel.priceData.shippingFee, isMinus = false)
        binding.tvPointDiscountValue.text = changeToPriceFormKr(orderViewModel.priceData.usePoint, isMinus = true)
        binding.tvCouponDiscountValue.text = changeToPriceFormKr(orderViewModel.priceData.couponDiscount, isMinus = true)
        binding.etCoupon.setText(orderViewModel.priceData.couponName ?: getString(R.string.none))
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
        val bootUser = BootUser().setPhone(orderViewModel.shippingInfo.phone)
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))
        Bootpay.init(this).setApplicationId(BuildConfig.BOOTPAY_KEY).setContext(this)
            .setBootUser(bootUser).setBootExtra(bootExtra).setUX(UX.PG_DIALOG).setPG(PG.INICIS).setMethod(Method.CARD)
            .setName(orderViewModel.getOrderName()).setOrderId(orderViewModel.orderIdx.toString()).setPrice(orderViewModel.priceData.calTotalPrice())
            .onDone { data ->
                orderViewModel.tryPostOrderVerification(parseReceiptId(data))
            }.onConfirm { message ->
                //showSimpleToastMessage(message)
                Bootpay.confirm(message)
            }
            .onReady { _ ->
                // 가상계좌로 결제 시, 가상계좌 발급이 완료되면 호출
                //showSimpleToastMessage(message)
            }
            .onCancel { _ -> // 결제 진행 중 사용자가 결제를 취소한 경우
                //showSimpleToastMessage(message)
            }
            .onError{ _ -> // 결제 진행 중 오류 발생시 호출 (활성화 하지 않은 pg, 한도초과, 카드 정지 등)
                //showSimpleToastMessage(message)
            }
            .onClose { _ ->

            }
            .request()
    }

    private fun parseReceiptId(bootPayString : String) : String {
        val jsonObject = JSONObject(bootPayString)
        return jsonObject.getString("receipt_id")
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}