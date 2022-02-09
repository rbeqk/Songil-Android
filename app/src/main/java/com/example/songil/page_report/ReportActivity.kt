package com.example.songil.page_report

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.ReportTarget
import com.example.songil.databinding.ReportActivityBinding
import com.example.songil.popup_report.ReportDialog
import com.example.songil.popup_warning.SocketTimeoutDialog

class ReportActivity : BaseActivity<ReportActivityBinding>(R.layout.report_activity) {

    private val checkBoxList = ArrayList<AppCompatCheckBox>()
    private val reportViewModel : ReportViewModel by lazy { ViewModelProvider(this)[ReportViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = reportViewModel
        binding.lifecycleOwner = this

        val target = intent.getSerializableExtra(GlobalApplication.REPORT_TARGET)
        val targetIdx = intent.getIntExtra(GlobalApplication.TARGET_IDX, -1)
        if (target != null){
            target as ReportTarget
            reportViewModel.setTarget(target, targetIdx)
            setTitleText(target)
        } else {
            Log.d("ReportActivity", "target is empty")
            finish()
            exitHorizontal
        }

        checkBoxList.addAll(arrayListOf(binding.cbProfanity, binding.cbPr, binding.cbIllegal, binding.cbObscenity, binding.cbExtrusion, binding.cbSpam, binding.cbSelfWrite))

        setEditText()
        setObserver()
        setButton()
    }

    private fun setTitleText(target : ReportTarget){
        if (target == ReportTarget.ABTEST_COMMENT || target == ReportTarget.QNA_COMMENT || target == ReportTarget.STORY_COMMENT || target == ReportTarget.ARTICLE_COMMENT)  {
            binding.navigationBar.text = getString(R.string.report_comment)
        }
        else if (target == ReportTarget.STORY)  {
            binding.navigationBar.text = getString(R.string.report_story)
        }
        else if(target == ReportTarget.ABTEST)  {
            binding.navigationBar.text = getString(R.string.report_abtest)
        }
        else if (target == ReportTarget.QNA)  {
            binding.navigationBar.text = getString(R.string.report_qna)
        }
        else if (target == ReportTarget.ARTICLE) {
            binding.navigationBar.text = getString(R.string.report_article)
        }
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnReport.setOnClickListener {
            reportViewModel.tryReport()
            reportViewModel.reportButtonActivate.value = false
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

        val reportResultObserver = Observer<Boolean>{ liveData ->
            reportViewModel.checkContent()
            if (liveData){
                val dialogFragment = ReportDialog()
                dialogFragment.show(supportFragmentManager, dialogFragment.tag)
            } else {
                val dialogFragment = SocketTimeoutDialog()
                dialogFragment.show(supportFragmentManager, dialogFragment.tag)
            }
        }
        reportViewModel.reportSuccess.observe(this, reportResultObserver)


    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}