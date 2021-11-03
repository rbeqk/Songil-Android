package com.example.songil.popup_sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.songil.databinding.BottomsheetSortBinding
import com.example.songil.popup_sort.popup_interface.PopupSortView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheet(private val view : PopupSortView, private val sort : String = "popular") : BottomSheetDialogFragment() {
    private var _binding : BottomsheetSortBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomsheetSortBinding.inflate(LayoutInflater.from(context))

        binding.tvbtnLowPrice.setOnClickListener {
            view.sort("price", "낮은 가격 순")
            dismiss()
        }
        binding.tvbtnPopular.setOnClickListener {
            view.sort("popular", "인기순")
            dismiss()
        }
        binding.tvbtnRecent.setOnClickListener {
            view.sort("latest", "최신순")
            dismiss()
        }
        binding.tvbtnReview.setOnClickListener {
            view.sort("review","리뷰 많은 순")
            dismiss()
        }

        when(sort){
            "popular" -> binding.ivPopular.visibility = View.VISIBLE
            "price" -> binding.ivLowPrice.visibility = View.VISIBLE
            "latest" -> binding.ivRecent.visibility = View.VISIBLE
            "review" -> binding.ivReview.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}