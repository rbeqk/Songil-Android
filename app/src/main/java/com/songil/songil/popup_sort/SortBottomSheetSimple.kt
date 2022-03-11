package com.songil.songil.popup_sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.songil.songil.databinding.BottomsheetSortBinding
import com.songil.songil.popup_sort.popup_interface.PopupSortView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetSimple(private val view : PopupSortView, private val sort : String = "popular") : BottomSheetDialogFragment() {
    private var _binding : BottomsheetSortBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetSortBinding.inflate(LayoutInflater.from(context))

        binding.tvbtnLowPrice.visibility = View.GONE
        binding.tvbtnPopular.setOnClickListener {
            view.sort("popular")
            dismiss()
        }
        binding.tvbtnRecent.setOnClickListener {
            view.sort("new")
            dismiss()
        }
        binding.tvbtnReview.visibility = View.GONE

        when(sort){
            "popular" -> binding.ivPopular.visibility = View.VISIBLE
            "new" -> binding.ivRecent.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}