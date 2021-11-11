package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class ShopRvNewCraftDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size3 = dpToPx(context, 3)
    private val size2 = dpToPx(context, 2)
    private val size1 = dpToPx(context, 1)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)

        when {
            position % 3 == 0 -> { outRect.right = size2 }
            position % 3 == 1 -> {
                outRect.left = size1
                outRect.right = size1
            }
            else -> { outRect.left = size2 }
        }

        if (position / 3 != 0){
            outRect.top =size3
        }

    }
}