package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class Craft1Decoration (context: Context, private val paging : Boolean = false) : RecyclerView.ItemDecoration() {
    private val size12 = dpToPx(context, 12)
    private val size2 = dpToPx(context, 2)
    private val size24 = dpToPx(context, 24)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        if (paging){
            outRect.left = size2
            outRect.right = size2
        } else {
            if (position % 2 == 1){
                outRect.left = size2
                outRect.right = size12
            } else {
                outRect.left = size12
                outRect.right = size2
            }
        }

        outRect.bottom = size24
    }
}