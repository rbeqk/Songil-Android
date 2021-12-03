package com.example.songil.recycler.rv_interface

interface RvCraftLikeView<T> {
    fun clickData(dataKey : T)

    fun clickLike(dataKey : T, position : Int)
}