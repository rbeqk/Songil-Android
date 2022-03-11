package com.songil.songil.recycler.rv_interface

interface RvAbTestView {
    fun vote(abTestIdx : Int, vote : String)

    fun cancelVote(abTestIdx : Int)
}