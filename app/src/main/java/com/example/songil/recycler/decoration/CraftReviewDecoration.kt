package com.example.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class CraftReviewDecoration(context : Context) : RecyclerView.ItemDecoration() {
    val size20 = dpToPx(context, 20)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)

        if (position != 0){
            outRect.top = size20
        }
    }
}