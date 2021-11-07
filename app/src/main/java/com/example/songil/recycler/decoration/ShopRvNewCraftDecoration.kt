package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class ShopRvNewCraftDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size3 = dpToPx(context, 3)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position % 3 != 0) outRect.left = size3

        outRect.bottom = size3
    }
}