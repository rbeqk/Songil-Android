package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class MainTrendDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size12 = dpToPx(context, 12)
    private val size4 = dpToPx(context, 4)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        if (position == 0){
            outRect.left = size12
        } else {
            outRect.left = size4
        }

        if (position == (parent.childCount - 1)){
            outRect.right = size12
        }
    }
}