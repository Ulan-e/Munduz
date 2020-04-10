package com.ulan.app.munduz.ui.basket

import com.ulan.app.munduz.ui.base.BasePresenter

interface BasketPresenter : BasePresenter {

    fun loadProducts()
    fun purchaseAllButtonClicked()
    fun decrementCount(sum: Int)
    fun incrementProduct(price: Int)
    fun countSumOfPurchases()
}