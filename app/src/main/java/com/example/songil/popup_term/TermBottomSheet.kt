package com.example.songil.popup_term

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.songil.databinding.BottomsheetTermBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TermBottomSheet : BottomSheetDialogFragment() {
    private var _binding : BottomsheetTermBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d =  super.onCreateDialog(savedInstanceState)

        d.setOnShowListener {
            setUpRatio()
        }
        (d as BottomSheetDialog).behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        return d
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetTermBinding.inflate(LayoutInflater.from(context))
        binding.tvTermContent.isNestedScrollingEnabled = true

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirmation.setOnClickListener {
            dismiss()
        }


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRatio(){
        val layoutParams = binding.layoutMain.layoutParams
        layoutParams.height = (getWindowHeight() * 85 / 100)
        binding.layoutMain.layoutParams = layoutParams
    }

    private fun getWindowHeight() : Int{
        var height = 0
        height = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            //val insets = windowMetrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
        return height

    }
}