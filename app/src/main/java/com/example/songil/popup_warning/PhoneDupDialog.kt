package com.example.songil.popup_warning

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.songil.R
import com.example.songil.databinding.PopupWarningBinding

class PhoneDupDialog() : DialogFragment() {
    private var _binding : PopupWarningBinding?= null
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

        binding.tvWarning.text = getString(R.string.duplicate_phone_number)
        binding.tvMessage.text = getString(R.string.duplicate_phone_number_message)
        binding.btnClose.text = getString(R.string.return_to_start)

        binding.btnClose.setOnClickListener {
            dismiss()
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}