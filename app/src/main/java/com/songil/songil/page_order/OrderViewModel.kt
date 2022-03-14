package com.songil.songil.page_order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.Craft4
import com.songil.songil.data.CraftAndAmount
import com.songil.songil.page_order.models.PriceData
import com.songil.songil.page_order.models.RequestBodyPostEtcInfo
import com.songil.songil.page_order.models.ShippingInfo
import kotlinx.coroutines.launch

class OrderViewModel : BaseViewModel() {

    private val repository = OrderRepository()

    var craftList = ArrayList<Craft4>()

    private val _getOrderInfoResult = MutableLiveData<Int>()
    val getOrderInfoResult : LiveData<Int> get() = _getOrderInfoResult

    private val _getExtraFeeResult = MutableLiveData<Int>()
    val getExtraFeeResult : LiveData<Int> get() = _getExtraFeeResult

    private val _applyBenefitResult = MutableLiveData<Int>()
    val applyBenefitResult : LiveData<Int> get() = _applyBenefitResult

    private val _postOrderInfoResult = MutableLiveData<Int>()
    val postOrderInfoResult : LiveData<Int> get() = _postOrderInfoResult

    private val _postOrderVerificationResult = MutableLiveData<Int>()
    val postOrderVerificationResult : LiveData<Int> get() = _postOrderVerificationResult

    var btnActivate = MutableLiveData(false)
    var orderIdx = 0

    val priceData = PriceData()
    val shippingInfo = ShippingInfo()

    // 주문서 정보 수신
    fun tryGetOrderForm(crafts : ArrayList<CraftAndAmount>){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getOrderData(crafts)
            if (result.body()?.code == 200){
                val data = result.body()!!.result
                craftList.clear()
                craftList.addAll(data.craft)
                priceData.havePoint = data.point
                priceData.shippingFee = data.totalBasicShippingFee
                priceData.craftTotalPrice = data.totalCraftPrice
                orderIdx = data.orderIdx
            }
            _getOrderInfoResult.value = (result.body()?.code ?: -1)
        }
    }

    // 우편 번호로 산간 지역 확인
    fun tryCheckExtraFee() {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.postExtraFee(orderIdx, shippingInfo.zipCode)
            if (result.body()?.code == 200){
                priceData.extraShippingFee = result.body()!!.result.totalExtraShippingFee
            }
            _getExtraFeeResult.postValue(result.body()?.code ?: -1)
        }
    }

    // 베네핏 (쿠폰) 적용
    fun tryApplyBenefit(benefitIdx : Int?){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.postBenefit(orderIdx, benefitIdx)
            if (result.body()?.code == 200){
                priceData.couponDiscount = result.body()!!.result.benefitDiscount
                priceData.couponName = result.body()!!.result.title
            }
            _applyBenefitResult.postValue(result.body()?.code ?: -1)
        }
    }

    // 결제 직전
    fun trySendOrderInfo(){
        viewModelScope.launch(exceptionHandler) {
            val tempBody = RequestBodyPostEtcInfo(shippingInfo.recipient, shippingInfo.phone, shippingInfo.address, shippingInfo.detailAddress, shippingInfo.memo, priceData.usePoint)
            val result = repository.postEtcInfo(orderIdx, tempBody)
            _postOrderInfoResult.postValue(result.body()?.code ?: -1)
        }
    }

    // 결제 검증 api
    fun tryPostOrderVerification(receiptIdx : String){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.postOrderVerification(orderIdx, receiptIdx)
            _postOrderVerificationResult.postValue(result.code)
        }
    }

    // 결제 버튼의 활성화 여부 설정
    fun checkBtnActivate(){
        btnActivate.value = (shippingInfo.recipient != "" && shippingInfo.phone != "" && shippingInfo.zipCode.length == 5 &&
                shippingInfo.address != "" && shippingInfo.detailAddress != "")
    }

    fun getOrderName() : String {
        return if (craftList.size == 1){
            craftList[0].name
        }
        else {
            "${craftList[0].name}외 ${craftList.size - 1}개"
        }
    }
}