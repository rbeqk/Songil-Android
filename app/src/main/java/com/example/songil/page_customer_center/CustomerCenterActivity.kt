package com.example.songil.page_customer_center

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.databinding.CustomercenterActivityBinding
import com.example.songil.popup_warning.SocketTimeoutDialog
import com.example.songil.recycler.adapter.NoticeAdapter

class CustomerCenterActivity : BaseActivity<CustomercenterActivityBinding>(R.layout.customercenter_activity) {
    private val viewModel : CustomerCenterViewModel by lazy { ViewModelProvider(this)[CustomerCenterViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setObserver()
        setButton()

        viewModel.tryGetFnq()
    }

    private fun setObserver(){
        val getFnqObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    (binding.rvFnq.adapter as NoticeAdapter).applyData(viewModel.fnqList)
                }
            }
        }
        viewModel.getFnqResult.observe(this, getFnqObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setRecyclerView(){
        binding.rvFnq.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvFnq.adapter = NoticeAdapter()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnKakao.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pf.kakao.com/_QAUBs"))
            startActivity(intent)
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}