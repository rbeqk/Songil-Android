package com.songil.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.utils.dpToPx

class Craft2Decoration(context : Context) : RecyclerView.ItemDecoration() {
    private val size4 = dpToPx(context, 4)
    private val size12 = dpToPx(context, 12)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val max = state.itemCount

        if (position == 0){
            outRect.left = size12
        } else {
            outRect.left = size4
        }

        if (position == max - 1){
            outRect.right = size12
        }
    }
}