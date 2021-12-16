package com.example.songil.page_mybenefit

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.Benefit
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.recycler.adapter.BenefitAdapter
import com.example.songil.recycler.decoration.BenefitDecoration

class MybenefitActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {
    private lateinit var viewModel : MybenefitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MybenefitViewModel::class.java]

        binding.tvTitle.text = "보유 베네핏"

        setRecyclerView()
        setObserver()

        binding.btnBack.setOnClickListener {
            finish()
        }

        viewModel.getBenefitData()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = BenefitAdapter(this)
        binding.rvContent.addItemDecoration(BenefitDecoration(this))
    }

    private fun setObserver(){
        val benefitObserver = Observer<ArrayList<Benefit>>{ liveData ->
            (binding.rvContent.adapter as BenefitAdapter).applyData(liveData)
        }
        viewModel.benefitDatas.observe(this, benefitObserver)
    }


}