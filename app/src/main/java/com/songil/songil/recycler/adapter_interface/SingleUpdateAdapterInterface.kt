package com.songil.songil.recycler.adapter_interface

import com.songil.songil.recycler.rv_interface.RvSingleUpdateView

interface SingleUpdateAdapterInterface {
    var singleUpdateView : RvSingleUpdateView ?

    fun attachSingleUpdateView(view : RvSingleUpdateView) {
        singleUpdateView = view
    }

    fun applySingleItemChange(position : Int, target : Any?)
}