package com.songil.songil.config

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.songil.songil.R
import java.text.DecimalFormat

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    @BindingAdapter("detailActivate")
    fun setDetailActivate(view : ViewGroup, isActivate: Boolean){
        if (isActivate){
            view.background = view.context.getDrawable(R.drawable.shape_select_detail_activate)
        } else {
            view.background = view.context.getDrawable(R.drawable.shape_select_detail_inactivate)
        }
    }

    @JvmStatic
    @BindingAdapter("isLoginText")
    fun setLoginTextBtn(view : TextView, isLogin : Boolean){
        if (!isLogin){
            view.setTextColor(view.context.getColor(R.color.g_3))
/*            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, NeedLoginActivity::class.java))
            }*/
            view.isClickable = false
        } else {
            view.setTextColor(view.context.getColor(R.color.songil_2))
            view.isClickable = true
        }
    }

    @JvmStatic
    @BindingAdapter("priceValue")
    fun setPriceIntText(view : TextView, price : Int){
        view.text = DecimalFormat("#,###").format(price)
    }

    @JvmStatic
    @BindingAdapter("simpleInterValue")
    fun setSimpleIntText(view : TextView, integer : Int) {
        view.text = integer.toString()
    }
}