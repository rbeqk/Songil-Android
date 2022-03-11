package com.songil.songil.popup_Yes_Or_No

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.songil.songil.config.CancelOrReturn
import com.songil.songil.databinding.PopupYesOrNoBinding

class CancelOrReturnDialog(private val title : String ?= null, private val cancelOrReturn: CancelOrReturn, private val isApprove : Boolean,
                           private val orderDetailIdx : Int, private val position : Int, private val clickYesEvent : (CancelOrReturn, Boolean, Int, Int) -> Unit) : DialogFragment() {
    private var _binding : PopupYesOrNoBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PopupYesOrNoBinding.inflate(inflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)

        title?.let { binding.tvConfirm.text = it }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setSize()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
            clickYesEvent(cancelOrReturn, isApprove, orderDetailIdx, position)
            dismiss()
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setSize(){
        val layoutParams = binding.layoutMain.layoutParams
        val size = getWindowSize()
        layoutParams.height = (size[1] * 4 / 10)
        layoutParams.width = (size[0] * 4 / 5)
        binding.layoutMain.layoutParams = layoutParams
    }

    private fun getWindowSize() : ArrayList<Int> {
        val height : Int
        val width : Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            //val insets = windowMetrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            height = windowMetrics.bounds.height()
            width = windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }
        return arrayListOf(width, height)
    }
}