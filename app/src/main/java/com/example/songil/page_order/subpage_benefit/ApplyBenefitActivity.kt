package com.example.songil.page_order.subpage_benefit

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.recycler.adapter.BenefitApplyAdapter
import com.example.songil.recycler.decoration.BenefitDecoration
import com.example.songil.recycler.rv_interface.RvClickView

class ApplyBenefitActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity), RvClickView {

    private val viewModel : ApplyBenefitViewModel by lazy { ViewModelProvider(this, ApplyBenefitViewModel.ApplyBenefitViewModelFactory(intent.getIntExtra("ORDER_IDX", 0)))[ApplyBenefitViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setButton()
        setObserver()

        viewModel.tryGetBenefit()
        binding.tvTitle.text = getString(R.string.my_benefit)
    }

    private fun setObserver(){
        val getBenefitObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    (binding.rvContent.adapter as BenefitApplyAdapter).applyData(viewModel.benefitList)
                }
            }
        }
        viewModel.getBenefitResult.observe(this, getBenefitObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = BenefitApplyAdapter(this, this)
        binding.rvContent.addItemDecoration(BenefitDecoration(this))
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            itemClick(-1)
        }
    }

    override fun itemClick(idx: Int) {
        val intent = Intent(this, BaseActivity::class.java)
        if (idx != -1)
            intent.putExtra("BENEFIT_IDX", idx)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

}