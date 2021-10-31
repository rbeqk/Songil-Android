package com.example.songil.popup_sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.songil.databinding.BottomsheetSortBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheet() : BottomSheetDialogFragment() {
    private var _binding : BottomsheetSortBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomsheetSortBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}