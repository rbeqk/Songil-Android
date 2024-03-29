package com.songil.songil.viewPager2.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.utils.dpToPx

class Vp2ArticleDecoration(context: Context) : RecyclerView.ItemDecoration() {
    val margin = dpToPx(context, 38)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = margin
        outRect.left = margin
    }

}