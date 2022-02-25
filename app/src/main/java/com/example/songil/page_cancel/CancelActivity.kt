package com.example.songil.page_cancel

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.CancelActivityBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.popup_cancel.CancelDialog
import com.example.songil.popup_warning.WarningDialog

class CancelActivity : BaseActivity<CancelActivityBinding>(R.layout.cancel_activity) {
    private lateinit var viewModel: CancelViewModel

    private val checkBoxList = ArrayList<AppCompatCheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderDetailIdx = intent.getIntExtra("ORDER_DETAIL_IDX", -1)
        viewModel = ViewModelProvider(this, CancelViewModel.CancelViewModelFactory(orderDetailIdx))[CancelViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        checkBoxList.addAll(arrayListOf(binding.cbSimpleRemorse, binding.cbOptionMistake, binding.cbConsultArtist, binding.cbReorder, binding.cbSelfWrite))

        setEditText()
        setObserver()
        setButton()
        viewModel.changeIdx(0)
    }

    private fun setObserver(){
        val idxObserver = Observer<Int>{ liveData ->
            for (i in 0 until checkBoxList.size){
                checkBoxList[i].isChecked = (liveData == i)
            }
            binding.etReason.isEnabled = (liveData == (checkBoxList.size - 1))
        }
        viewModel.reasonIdx.observe(this, idxObserver)

        val cancelObserver = Observer<Int>{ resultCode ->
            viewModel.checkBtnActivate()
            when (resultCode){
                200 -> {
                    val dialogFragment = CancelDialog()
                    dialogFragment.show(supportFragmentManager, dialogFragment.tag)
                }
                3000 -> { // jwt 기한 만료
                    val dialog = WarningDialog("자동로그인이 해제되었습니다.", "마지막 로그인 및 자동로그인 이후\n30일이 경과되어 로그아웃되었습니다."){
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
                }
                3502 -> { // 주문취소 불가능
                    val dialog = WarningDialog("취소가 불가능한 주문입니다.", "삭제되거나 이미 발송중인 주문입니다."){
                        finish()
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
                }
                2340 -> { // 존재하지 않는 주문 idx
                    val dialog = WarningDialog("조회할 수 없는 주문입니다.", "삭제되거나 작가정보를 조회할 수 없는 주문입니다."){
                        finish()
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
                }
            }
        }
        viewModel.sendCancelRequestResult.observe(this, cancelObserver)
    }

    private fun setEditText(){
        binding.etReason.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkBtnActivate()
            }
        })
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCancel.setOnClickListener {
            viewModel.deactivateButton()
            viewModel.trySendCancelRequest()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}