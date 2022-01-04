package com.example.songil.custom_layout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

class LockableNestedScrollView(context: Context, attributeSet: AttributeSet?) : NestedScrollView(context, attributeSet) {

    private var lastX = 0f
    private var lastY = 0f
    private val configuration = ViewConfiguration.get(context)
    private var thres = 0

    init {
        thres = configuration.scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var allowScroll = true
        super.onInterceptTouchEvent(ev)
        if (ev != null) {
            when (ev.actionMasked){
                MotionEvent.ACTION_DOWN -> {
                    lastX = ev.x
                    lastY = ev.y
                    super.onTouchEvent(ev)
                    allowScroll = false
                }
                MotionEvent.ACTION_MOVE -> {
                    val currentX = ev.x
                    val currentY = ev.y
                    val dx = abs(lastX - currentX)
                    val dy = abs(lastY - currentY)
                    allowScroll = (dy > dx && dy > thres)
                }
                (MotionEvent.ACTION_UP) -> {
                    allowScroll = false
                }
            }
        }
        return allowScroll
    }

}