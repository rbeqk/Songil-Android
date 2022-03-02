package com.example.songil.recycler.rv_interface

interface RvUserOrderStatusView {
    fun requestCancel(parentPosition : Int, childPosition : Int, orderDetailIdx : Int)

    fun requestReturn(parentPosition : Int, childPosition : Int, orderDetailIdx : Int)

    fun goToCommentWrite(parentPosition : Int, childPosition : Int, orderDetailIdx : Int)
}