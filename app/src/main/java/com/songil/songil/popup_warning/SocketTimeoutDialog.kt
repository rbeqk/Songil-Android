package com.songil.songil.popup_warning

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
import com.songil.songil.R
import com.songil.songil.databinding.PopupWarningBinding

class SocketTimeoutDialog(private val afterClose : Boolean = false) : DialogFragment() {
    private var _binding : PopupWarningBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PopupWarningBinding.inflate(LayoutInflater.from(context))

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvWarning.text = getString(R.string.socket_time_out)
        binding.tvMessage.text = getString(R.string.socket_time_out_message)
        binding.btnClose.text = getString(R.string.confirmation)

        binding.btnClose.setOnClickListener {
            dismiss()
            if (afterClose) activity?.finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        setSize()
    }

    private fun setSize(){
        val layoutParams = binding.layoutMain.layoutParams
        val size = getWindowSize()
        layoutParams.height = (size[1] * 4 / 10)
        layoutParams.width = (size[0] * 7 / 10)
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