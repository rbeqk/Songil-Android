package com.example.songil.recycler.rv_interface

interface RvTriggerView {
    fun notifyDataChange(type : Int, position : Int? = null)
}