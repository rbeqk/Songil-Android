package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class ShopCategoryDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size1 = dpToPx(context, 1)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        if (position % 4 != 0){
            outRect.left = size1
        }
        if (position / 4 != 0){
            outRect.top = size1
        }
    }
}