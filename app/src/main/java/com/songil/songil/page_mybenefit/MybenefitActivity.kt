package com.songil.songil.page_mybenefit

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.data.Benefit
import com.songil.songil.databinding.MydetailActivityBinding
import com.songil.songil.recycler.adapter.BenefitAdapter
import com.songil.songil.recycler.decoration.BenefitDecoration

class MybenefitActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {
    private lateinit var viewModel : MybenefitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MybenefitViewModel::class.java]

        binding.tvTitle.text = getString(R.string.my_benefit)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_benefit)

        setRecyclerView()
        setObserver()
        setButton()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetMyBenefit()
        }

        viewModel.tryGetMyBenefit()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = BenefitAdapter(this)
        binding.rvContent.addItemDecoration(BenefitDecoration(this))
    }

    private fun setObserver(){
        val benefitObserver = Observer<ArrayList<Benefit>>{ liveData ->
            binding.layoutRefresh.isRefreshing = false
            if (liveData.size == 0){
                binding.viewEmpty.root.visibility = View.VISIBLE
            } else {
                binding.viewEmpty.root.visibility = View.GONE
            }
            (binding.rvContent.adapter as BenefitAdapter).applyData(liveData)
        }
        viewModel.benefitDatas.observe(this, benefitObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}