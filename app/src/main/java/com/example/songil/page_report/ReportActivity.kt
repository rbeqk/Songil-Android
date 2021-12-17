package com.example.songil.page_report

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ReportActivityBinding
import com.example.songil.popup_report.ReportDialog

class ReportActivity : BaseActivity<ReportActivityBinding>(R.layout.report_activity) {

    private val checkBoxList = ArrayList<AppCompatCheckBox>()
    private val reportViewModel : ReportViewModel by lazy { ViewModelProvider(this)[ReportViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = reportViewModel
        binding.lifecycleOwner = this

        checkBoxList.addAll(arrayListOf(binding.cbProfanity, binding.cbPr, binding.cbIllegal, binding.cbObscenity, binding.cbExtrusion, binding.cbSpam, binding.cbSelfWrite))

        setEditText()
        setObserver()
        setButton()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnReport.setOnClickListener {
            val dialogFragment = ReportDialog()
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }
    }

    private fun setEditText(){
        binding.etReason.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                reportViewModel.checkContent()
            }

        })
    }

    private fun setObserver(){
        val idxObserver = Observer<Int>{ liveData ->
            for (i in 0 until checkBoxList.size){
                checkBoxList[i].isChecked = (liveData == i)
            }
            binding.etReason.isEnabled = (liveData == (checkBoxList.size - 1))
        }
        reportViewModel.reasonIdx.observe(this, idxObserver)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.none, R.anim.to_right)
    }
}