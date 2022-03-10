package com.example.songil.page_return

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.databinding.ReturnActivityBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.popup_cancel.CancelDialog
import com.example.songil.popup_warning.WarningDialog

class ReturnActivity : BaseActivity<ReturnActivityBinding>(R.layout.return_activity) {

    private val viewModel : ReturnViewModel by lazy { ViewModelProvider(this, ReturnViewModel.ReturnViewModelFactory(
            intent.getIntExtra("ORDER_DETAIL_IDX", -1)
    ))[ReturnViewModel::class.java] }

    private val checkBoxList = arrayListOf<AppCompatCheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        checkBoxList.addAll(arrayListOf(binding.cbSimpleRemorse, binding.cbDamageDuringDelivery, binding.cbConsultArtist, binding.cbSelfWrite))

        setButton()
        setObserver()
        setEditText()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRequestReturn.setOnClickListener {
            viewModel.deactivateButton()
            viewModel.trySendReturnRequest()
        }
    }

    private fun setObserver() {
        val idxObserver = Observer<Int>{ liveData ->
            for (i in 0 until checkBoxList.size){
                checkBoxList[i].isChecked = (liveData == i)
            }
            binding.etReason.isEnabled = (liveData == (checkBoxList.size - 1))
        }
        viewModel.reasonIdx.observe(this, idxObserver)

        val returnObserver = Observer<Int> { resultCode ->
            viewModel.checkBtnActivate()
            when(resultCode){
                200 -> {
                    val intent = Intent(this, BaseActivity::class.java)
                    setResult(RESULT_OK, intent)
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
        viewModel.sendReturnRequestResult.observe(this, returnObserver)

        val networkErrorObserver = Observer<BaseViewModel.FetchState>{ fetchState ->
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

    private fun setEditText(){
        binding.etReason.addTextChangedListener(object : TextWatcher {
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
}