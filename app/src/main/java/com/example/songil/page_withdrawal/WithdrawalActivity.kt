package com.example.songil.page_withdrawal

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.WithdrawalActivityBinding

class WithdrawalActivity : BaseActivity<WithdrawalActivityBinding>(R.layout.withdrawal_activity) {
    private lateinit var viewModel: WithdrawalVieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[WithdrawalVieModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }
}