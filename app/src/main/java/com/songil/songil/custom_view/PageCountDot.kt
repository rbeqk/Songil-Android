package com.songil.songil.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.songil.songil.R
import com.songil.songil.databinding.ViewPageCountDotBinding
import com.songil.songil.utils.dpToPx

class PageCountDot(context : Context, attrs : AttributeSet) : LinearLayout(context, attrs) {

    private val size8 = dpToPx(context, 8)
    private val size4 = dpToPx(context, 4)
    private val binding = ViewPageCountDotBinding.inflate(LayoutInflater.from(context), this, true)
    private val dotList = ArrayList<View>()
    private var current = 0


    fun initialSetting(count : Int){
        for (i in 0 until count){
            val dot = View(context)
            if (i == 0){
                dot.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                dot.layoutParams.height = size4
                dot.layoutParams.width = size8
                dot.background = ContextCompat.getDrawable(context, R.drawable.shape_rectangle_black_radius_4)
            }
            else {
                val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                lp.setMargins(size8, 0, 0, 0)
                dot.layoutParams = lp
                dot.layoutParams.height = size4
                dot.layoutParams.width = size4
                dot.background = ContextCompat.getDrawable(context, R.drawable.shape_rectanble_white_radius_4)
            }
            dotList.add(dot)
            binding.layoutMain.addView(dot)
        }
        invalidate()
    }

    fun changeIdx(Idx : Int){
        dotList[current].layoutParams.width = size4
        dotList[current].background = ContextCompat.getDrawable(context, R.drawable.shape_rectanble_white_radius_4)

        dotList[Idx].layoutParams.width = size8
        dotList[Idx].background = ContextCompat.getDrawable(context, R.drawable.shape_rectangle_black_radius_4)

        binding.layoutMain.requestLayout()
        current = Idx
    }
}