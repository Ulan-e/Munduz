package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.developer.Product

interface BuyPresenter {

    fun setProduct(product: Product)
    fun sendButtonClicked()
    fun cancelButtonClicked()
    fun detachView()
}