package com.songil.songil.page_with

interface WithSubFragmentInterface {
    // call when beginTransaction.show
    fun onShow()

    // call when sort button in WithFragment clicked
    fun sort(sort : String)

    // to call sort in specific fragment
    fun getSort() : String

    // call after write post
    fun reload()
}