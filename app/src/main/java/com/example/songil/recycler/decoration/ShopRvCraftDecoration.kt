package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class ShopRvCraftDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size4 = dpToPx(context, 4)
    private val size24 = dpToPx(context, 24)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        if (position % 2 == 1){
            outRect.left = size4
        }

        if (position / 2 != 0){
            outRect.top = size24
        }
    }
}