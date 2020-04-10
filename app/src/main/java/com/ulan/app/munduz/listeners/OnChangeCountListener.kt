package com.ulan.app.munduz.listeners

interface OnChangeCountListener {

    fun changeSumOfPurchases(sum: Int)
    fun decrementProduct(price: Int)
    fun incrementProduct(price: Int)
}