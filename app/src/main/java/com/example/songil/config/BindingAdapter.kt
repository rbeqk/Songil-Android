package com.example.songil.config

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.songil.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("textBtnActivate")
    fun setActivate(view : TextView, isActivate : Boolean){
        if (isActivate){
            view.setTextColor(view.context.getColor(R.color.songil_4))
            view.setBackgroundColor(view.context.getColor(R.color.songil_2))
        } else {
            view.setTextColor(view.context.getColor(R.color.songil_1))
            view.setBackgroundColor(view.context.getColor(R.color.g_3))
        }
        view.isClickable = isActivate
    }

    // testing
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    @BindingAdapter("detailActivate")
    fun setDetailActivate(view : ViewGroup, isActivate: Boolean){
        Log.d("bindingAdapter activate", "is called $isActivate")
        if (isActivate){
            view.background = view.context.getDrawable(R.drawable.shape_select_detail_activate)
        } else {
            view.background = view.context.getDrawable(R.drawable.shape_select_detail_inactivate)
        }
    }
}