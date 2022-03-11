package com.songil.songil.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.utils.dpToPx

class HotTalkDecoration(context : Context) : RecyclerView.ItemDecoration() {
    private val size20 = dpToPx(context, 20)
    private val size8 = dpToPx(context, 8)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0){
            outRect.left = size20
        } else {
            outRect.left = size8
        }

        if (position == state.itemCount - 1){
            outRect.right = size20
        }
    }
}