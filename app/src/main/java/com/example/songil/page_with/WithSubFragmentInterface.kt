package com.example.songil.page_with

interface WithSubFragmentInterface {
    // call when beginTransaction.show
    fun onShow()

    // call when sort button in WithFragment clicked
    fun sort(sort : String)
}