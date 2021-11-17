package com.example.songil.page_cancel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.CancelActivityBinding
import com.example.songil.popup_cancel.CancelDialog

class CancelActivity : BaseActivity<CancelActivityBinding>(R.layout.cancel_activity) {
    private lateinit var viewModel: CancelViewModel

    private val checkBoxList = ArrayList<AppCompatCheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[CancelViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        checkBoxList.addAll(arrayListOf(binding.cbSimpleRemorse, binding.cbOptionMistake, binding.cbConsultArtist, binding.cbReorder, binding.cbSelfWrite))

        setEditText()
        setObserver()
        setButton()
        viewModel.changeIdx(0)


    }

    private fun setObserver(){
        val idxObserver = Observer<Int>{ liveData ->
            for (i in 0 until checkBoxList.size){
                checkBoxList[i].isChecked = (liveData == i)
            }
            binding.etReason.isEnabled = (liveData == (checkBoxList.size - 1))
        }
        viewModel.reasonIdx.observe(this, idxObserver)
    }

    private fun setEditText(){
        binding.etReason.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkContent()
            }
        })
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnCancel.setOnClickListener {
            val dialogFragment = CancelDialog()
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }
    }
}