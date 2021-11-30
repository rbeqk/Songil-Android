package com.example.songil.page_with

import android.os.Bundle
import android.view.View
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.WithFragmentMainBinding
import com.example.songil.popup_sort.SortBottomSheetSimple
import com.example.songil.popup_sort.popup_interface.PopupSortView

class WithFragment : BaseFragment<WithFragmentMainBinding>(WithFragmentMainBinding::bind, R.layout.with_fragment_main), PopupSortView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
    }

    private fun setButton(){
        binding.btnSort.setOnClickListener {
            val dialog = SortBottomSheetSimple(this)
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    override fun sort(sort: String, originalWord: String) {

    }
}