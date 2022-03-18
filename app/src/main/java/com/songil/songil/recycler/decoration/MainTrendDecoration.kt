package com.songil.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.utils.dpToPx

class MainTrendDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size12 = dpToPx(context, 12)
    private val size4 = dpToPx(context, 4)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        if (position == 0){
            outRect.left = size12
        } else {
            outRect.left = size4
        }

        if (position == (state.itemCount - 1)){
            outRect.right = size12
        }
    }
}