package com.example.songil.page_shippinginfo

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ShippinginfoActivityInputBinding
import java.util.*

class ShippingInfoActivity : BaseActivity<ShippinginfoActivityInputBinding>(R.layout.shippinginfo_activity_input) {
    private var datePickerIsShow = false
    private val viewModel : ShippingInfoViewModel by lazy {
        ViewModelProvider(this, ShippingInfoViewModel.ShippingInfoViewModelFactory(intent.getIntExtra("ORDER_DETAIL_IDX", -1)))[ShippingInfoViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setDatePicker()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.etYear.setOnClickListener {
            showDatePicker()
        }

        binding.etMonth.setOnClickListener {
            showDatePicker()
        }

        binding.etDay.setOnClickListener {
            showDatePicker()
        }

        binding.tvbtnDateYes.setOnClickListener {
            viewModel.setDate(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth)
            binding.etYear.text = binding.datePicker.year.toString()
            binding.etMonth.text = (binding.datePicker.month + 1).toString()
            binding.etDay.text = (binding.datePicker.dayOfMonth).toString()
            hideDatePicker()
        }

        binding.tvbtnDateNo.setOnClickListener {
            hideDatePicker()
        }
        binding.backgroundFilter.setOnClickListener {
            hideDatePicker()
        }

        binding.btnAnswer.setOnClickListener {
            showSimpleToastMessage(viewModel.tempGetStatusString())
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    // ab-test write activity 에 있는 것과 유사
    private fun setDatePicker(){
        val datePickerListener =
                DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                    //viewModel.setDate(year, monthOfYear, dayOfMonth)
                }
        val current = Calendar.getInstance()

        binding.datePicker.init(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), datePickerListener)
    }

    // ab-test write activity 에 있는 것과 동일
    private fun hideDatePicker(){
        datePickerIsShow = false

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
        datePickerIsShow = true

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
}