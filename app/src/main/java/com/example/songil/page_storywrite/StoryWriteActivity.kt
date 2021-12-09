package com.example.songil.page_storywrite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.StoryActivityWriteBinding
import com.example.songil.utils.dpToPx
import com.google.android.material.chip.Chip

class StoryWriteActivity : BaseActivity<StoryActivityWriteBinding>(R.layout.story_activity_write){
    private lateinit var viewModel : StoryWriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[StoryWriteViewModel::class.java]

        setEditText()
    }

    private fun setEditText(){
        binding.etTag.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                addChip(binding.etTag.text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun addChip(word : String){
        if (word != ""){
            Log.d("chip", word)
            val chip = Chip(this)
            chip.text = word
            chip.isCloseIconVisible = true
            chip.textSize = 12f
            chip.setChipBackgroundColorResource(R.color.g_2)
            chip.isCheckable = false
            chip.setOnCloseIconClickListener {
                binding.cgTag.removeView(chip)
            }
            binding.cgTag.addView(chip)
            binding.cgTag.invalidate()
            binding.etTag.setText("")
        }
    }
}