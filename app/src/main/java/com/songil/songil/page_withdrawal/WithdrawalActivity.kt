package com.songil.songil.page_withdrawal

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.WithdrawalActivityBinding

class WithdrawalActivity : BaseActivity<WithdrawalActivityBinding>(R.layout.withdrawal_activity) {
    private val viewModel: WithdrawalViewModel by lazy { ViewModelProvider(this)[WithdrawalViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setButton()
        setObserver()
    }

    private fun setObserver(){
        val withdrawalResultObserver = Observer<Int> { resultCode ->
            when (resultCode) {
                200 -> {
                    val edit = GlobalApplication.globalSharedPreferences.edit()
                    edit.remove(GlobalApplication.X_ACCESS_TOKEN)
                    edit.remove(GlobalApplication.IS_ARTIST)
                    edit.apply()
                    showSimpleToastMessage("회원탈퇴가 완료되었습니다.")
                    finish()
                }
            }
        }
        viewModel.withdrawalResult.observe(this, withdrawalResultObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnWithdrawal.setOnClickListener {
            viewModel.tryWithdrawal()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}