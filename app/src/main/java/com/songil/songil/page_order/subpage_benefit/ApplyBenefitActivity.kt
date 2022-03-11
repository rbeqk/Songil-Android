package com.songil.songil.page_order.subpage_benefit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.SimpleBaseActivityBinding
import com.songil.songil.recycler.adapter.BenefitApplyAdapter
import com.songil.songil.recycler.decoration.BenefitDecoration
import com.songil.songil.recycler.rv_interface.RvClickView

class ApplyBenefitActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity), RvClickView {

    private val viewModel : ApplyBenefitViewModel by lazy { ViewModelProvider(this, ApplyBenefitViewModel.ApplyBenefitViewModelFactory(intent.getIntExtra("ORDER_IDX", 0)))[ApplyBenefitViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setButton()
        setObserver()

        viewModel.tryGetBenefit()
        binding.tvTitle.text = getString(R.string.my_benefit)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_enable_benefit)
    }

    private fun setObserver(){
        val getBenefitObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    if (viewModel.benefitList.size == 0){
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    } else {
                        binding.viewEmpty.root.visibility = View.GONE
                        (binding.rvContent.adapter as BenefitApplyAdapter).applyData(viewModel.benefitList)
                    }
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