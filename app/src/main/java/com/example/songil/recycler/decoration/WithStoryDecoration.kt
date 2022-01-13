package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class WithStoryDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size16 = dpToPx(context, 16)
    private val size2 = dpToPx(context, 2)
    private val size24 = dpToPx(context, 24)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position % 2 == 0){
            outRect.left = size16
            outRect.right = size2
        } else {
            outRect.right = size16
            outRect.left = size2
        }
        outRect.bottom = size24
    }
}