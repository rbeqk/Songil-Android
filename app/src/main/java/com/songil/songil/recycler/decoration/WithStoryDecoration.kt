package com.songil.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.utils.dpToPx

class WithStoryDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size2 = dpToPx(context, 2)
    private val size24 = dpToPx(context, 24)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.right = size2
        outRect.left = size2
        outRect.bottom = size24
    }
}