package com.songil.songil.recycler.rv_interface

interface RvTriggerView {
    fun notifyDataChange(type : Int, position : Int? = null)
}