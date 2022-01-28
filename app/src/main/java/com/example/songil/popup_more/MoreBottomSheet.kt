package com.example.songil.popup_more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.songil.databinding.BottomsheetMoreBinding
import com.example.songil.popup_more.popup_interface.PopupMoreView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MoreBottomSheet(private val view : PopupMoreView, private val isWriter : Boolean = false) : BottomSheetDialogFragment() {
    private var _binding : BottomsheetMoreBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomsheetMoreBinding.inflate(inflater)

        setButton()
        setView()

        return binding.root
    }

    private fun setView(){
        if (isWriter){
            binding.tvbtnReport.visibility = View.GONE
            binding.lineRemove.visibility = View.GONE
        } else {
            binding.tvbtnRemove.visibility = View.GONE
            binding.lineRemove.visibility = View.GONE
            binding.tvbtnModify.visibility = View.GONE
            binding.lineModify.visibility = View.GONE
        }
    }

    private fun setButton(){
        binding.tvbtnModify.setOnClickListener {
            view.bottomSheetModifyClick()
            dismiss()
        }

        binding.tvbtnRemove.setOnClickListener {
            view.bottomSheetRemoveClick()
            dismiss()
        }

        binding.tvbtnReport.setOnClickListener {
            view.bottomSheetReportClick()
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}