package com.example.songil.config

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
}