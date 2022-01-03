package com.example.songil.popup_warning

import android.content.Intent
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
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.PopupWarningBinding
import com.example.songil.page_needlogin.NeedLoginActivity

class NeedLoginDialog : DialogFragment() {
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

        binding.btnCancel.visibility = View.VISIBLE

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnClose.setOnClickListener {
            (activity as BaseActivity<*>).startActivityVertical(Intent(activity, NeedLoginActivity::class.java))
            dismiss()
        }

        binding.btnClose.text = getString(R.string.login_slash_signup)
        binding.tvWarning.text = getString(R.string.service_need_login)
        binding.tvMessage.text = getString(R.string.service_need_login_messsage)
    }

    override fun onResume() {
        super.onResume()
        setSize()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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