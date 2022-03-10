package com.example.songil.page_shippinginfo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ShippinginfoActivityInputBinding
import com.example.songil.recycler.adapter.CourierSelectAdapter
import java.util.*

class ShippingInfoActivity : BaseActivity<ShippinginfoActivityInputBinding>(R.layout.shippinginfo_activity_input) {
    private fun datePickerIsShow() = binding.layoutDatePicker.visibility == View.VISIBLE
    private fun selectCourierLayoutIsShow() = binding.layoutSelectCourierCompany.visibility == View.VISIBLE
    private val viewModel : ShippingInfoViewModel by lazy {
        ViewModelProvider(this, ShippingInfoViewModel.ShippingInfoViewModelFactory(intent.getIntExtra("ORDER_DETAIL_IDX", -1)))[ShippingInfoViewModel::class.java] }

    // animation 이 진행되는 동안 택배사 선택 클릭이벤트를 막기 위함
    private var selectCourierClickEnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setDatePicker()
        setRecyclerView()
        setEditText()
        setObserver()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // 택배사 선택 레이아웃의 높이를 총 높이의 80%로 설정
        val selectCourierLayoutParams = binding.layoutSelectCourierCompany.layoutParams
        selectCourierLayoutParams.height = (getWindowSize()[1] * 0.8).toInt()
        binding.layoutSelectCourierCompany.layoutParams = selectCourierLayoutParams
    }

    private fun setObserver(){
        val uploadResultObserver = Observer<Int> { resultCode ->
            viewModel.checkBtnActivate()
            when (resultCode) {
                200 -> {
                    showSimpleToastMessage("배송정보가 입력되었습니다.")
                    val intent = Intent(this, BaseActivity::class.java)
                    setResult(RESULT_OK, intent)
                    finish()
                }
                3501 -> { // 유효하지 않은 운송장 번호 혹은 택배사 코드
                    showSimpleToastMessage("유효하지 않은 운송장 번호 혹은 택배사입니다.")
                }
                2361 -> {
                    showSimpleToastMessage("유효한 날짜를 입력해주세요.")
                }
            }
        }
        viewModel.sendingInfoResult.observe(this, uploadResultObserver)

        val networkErrorObserver = Observer<BaseViewModel.FetchState> { fetchState ->
            viewModel.checkBtnActivate()
            when (fetchState){
                BaseViewModel.FetchState.BAD_INTERNET -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.FAIL -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.WRONG_CONNECTION -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.PARSE_ERROR -> {}
                null -> {}
            }
        }
        viewModel.fetchState.observe(this, networkErrorObserver)
    }

    private fun setRecyclerView(){
        binding.rvSelectCourierCompany.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSelectCourierCompany.adapter = CourierSelectAdapter(GlobalApplication.courierMap, ::selectCourierCompany)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener { finish() }

        binding.etYear.setOnClickListener { showDatePicker() }

        binding.etMonth.setOnClickListener { showDatePicker() }

        binding.etDay.setOnClickListener { showDatePicker() }

        binding.tvbtnDateYes.setOnClickListener {
            viewModel.setDate(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth)
            binding.etYear.text = binding.datePicker.year.toString()
            binding.etMonth.text = (binding.datePicker.month + 1).toString()
            binding.etDay.text = (binding.datePicker.dayOfMonth).toString()
            viewModel.checkBtnActivate()
            hideDatePicker()
        }

        binding.tvbtnDateNo.setOnClickListener { hideDatePicker() }

        binding.backgroundFilter.setOnClickListener {
            if (datePickerIsShow()){
                hideDatePicker()
            }
            if (selectCourierLayoutIsShow()){
                hideSelectCourierCompanyLayout()
            }
        }

        binding.tvbtnCourierCompany.setOnClickListener { showSelectCourierCompanyLayout() }

        binding.ivCloseSelectCourier.setOnClickListener { hideSelectCourierCompanyLayout() }

        binding.btnAnswer.setOnClickListener {
            viewModel.deactivateBtn()
            viewModel.tryUploadSendingInfo()
        }
    }

    private fun setEditText(){
        binding.etWaybillNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkBtnActivate()
            }
        })
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    // ab-test write activity 에 있는 것과 유사
    private fun setDatePicker(){
        val datePickerListener =
                DatePicker.OnDateChangedListener { _, _, _, _ -> // _, year, monthOfYear, dayOfMonth
                    //viewModel.setDate(year, monthOfYear, dayOfMonth)
                }
        val current = Calendar.getInstance()

        binding.datePicker.init(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), datePickerListener)
    }

    // ab-test write activity 에 있는 것과 동일
    private fun hideDatePicker(){
        val backgroundAnim = AlphaAnimation(1f, 0f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = false
        binding.backgroundFilter.animation = backgroundAnim
        binding.backgroundFilter.visibility = View.GONE

        val anim = TranslateAnimation(0f, 0f, 0f, binding.layoutDatePicker.height.toFloat())
        anim.duration = 350
        anim.fillAfter = false
        binding.layoutDatePicker.animation = anim
        binding.layoutDatePicker.visibility = View.GONE
    }

    // ab-test write activity 에 있는 것과 동일
    private fun showDatePicker(){
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etWaybillNumber.windowToken, 0)

        val backgroundAnim = AlphaAnimation(0f, 1f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = true
        binding.backgroundFilter.animation = backgroundAnim
        binding.backgroundFilter.visibility = View.VISIBLE

        val anim = TranslateAnimation(0f, 0f, binding.layoutDatePicker.height.toFloat(), 0f)
        anim.duration = 350
        anim.fillAfter = true
        binding.layoutDatePicker.animation = anim
        binding.layoutDatePicker.visibility = View.VISIBLE
    }

    // 택배사 선택 레이아웃 보임
    private fun showSelectCourierCompanyLayout(){
        selectCourierClickEnable = true
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etWaybillNumber.windowToken, 0)

        val backgroundAnim = AlphaAnimation(0f, 1f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = true
        binding.backgroundFilter.animation = backgroundAnim
        binding.backgroundFilter.visibility = View.VISIBLE

        val anim = TranslateAnimation(0f, 0f, binding.layoutSelectCourierCompany.height.toFloat(), 0f)
        anim.duration = 350
        anim.fillAfter = true
        binding.layoutSelectCourierCompany.animation = anim
        binding.layoutSelectCourierCompany.visibility = View.VISIBLE
    }

    // 택배사 선택 레이아웃 숨김
    private fun hideSelectCourierCompanyLayout(){
        selectCourierClickEnable = false
        val backgroundAnim = AlphaAnimation(1f, 0f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = false
        binding.backgroundFilter.animation = backgroundAnim
        binding.backgroundFilter.visibility = View.GONE

        val anim = TranslateAnimation(0f, 0f, 0f, binding.layoutSelectCourierCompany.height.toFloat())
        anim.duration = 350
        anim.fillAfter = false
        binding.layoutSelectCourierCompany.animation = anim
        binding.layoutSelectCourierCompany.visibility = View.GONE
    }

    // 화면의 가로 세로 높이를 pixel 값으로 리턴
    private fun getWindowSize() : ArrayList<Int> {
        val height : Int
        val width : Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val displayMetrics = resources.displayMetrics
            height = displayMetrics.heightPixels//windowMetrics.bounds.height()
            width = displayMetrics.widthPixels//windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }
        return arrayListOf(width, height)
    }

    // 택배사 선택 레이아웃에서 아이템 클릭시 호출되는 함수
    private fun selectCourierCompany(key: String){
        if (selectCourierClickEnable) {
            hideSelectCourierCompanyLayout()
            binding.tvbtnCourierCompany.text = GlobalApplication.courierMap[key]
                    ?: "택배사를 찾을 수 없습니다."
            viewModel.tCode = key
            viewModel.checkBtnActivate()
        }
    }
}