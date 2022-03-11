package com.songil.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.utils.dpToPx

class SimpleImageDecoration(context : Context) : RecyclerView.ItemDecoration() {
    private val size16 = dpToPx(context, 16)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        if (position != 0){
            outRect.top = size16
        }
    }
}