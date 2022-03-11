package com.songil.songil.recycler.rv_interface

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface RvSingleUpdateView {
    var recentTargetPosition : Int
    val singleItemUpdateCallback : ActivityResultLauncher<Intent>
    fun targetItemClick(position : Int, targetIdx : Int)
}