package com.example.songil.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.utils.dpToPx

class ArticleViewPager2ItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
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