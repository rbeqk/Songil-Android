package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class ShopRvCategoryTextItemDecoration(context : Context) : RecyclerView.ItemDecoration() {
    val size24 = dpToPx(context, 24)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        //val position = parent.getChildLayoutPosition(view)

        outRect.bottom = size24
    }
}