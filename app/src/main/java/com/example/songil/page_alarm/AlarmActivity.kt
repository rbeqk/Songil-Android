package com.example.songil.page_alarm

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.WithNotice
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.recycler.adapter.AlarmAdapter

class AlarmActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity){

    private val viewModel : AlarmViewModel by lazy { ViewModelProvider(this)[AlarmViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setRecyclerView()
        setObserver()

        viewModel.tryGetNotice()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = AlarmAdapter()
    }

    private fun setObserver(){
        val noticeObserver = Observer<ArrayList<WithNotice>>{ liveData ->
            (binding.rvContent.adapter as AlarmAdapter).applyData(liveData)
        }
        viewModel.noticeList.observe(this, noticeObserver)
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}