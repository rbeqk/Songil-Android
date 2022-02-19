package com.example.songil.recycler.adapter_interface

import com.example.songil.recycler.rv_interface.RvSingleUpdateView

interface SingleUpdateAdapterInterface {
    var singleUpdateView : RvSingleUpdateView ?

    fun attachSingleUpdateView(view : RvSingleUpdateView) {
        singleUpdateView = view
    }

    fun applySingleItemChange(position : Int, target : Any?)
}