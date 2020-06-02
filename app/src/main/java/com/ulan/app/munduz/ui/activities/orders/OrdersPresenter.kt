package com.ulan.app.munduz.ui.activities.orders

import com.ulan.app.munduz.data.models.PurchaseEntity

interface OrdersPresenter {

    fun setToolbar()

    fun setProducts(purchases: MutableList<PurchaseEntity>)

    fun setPurchasesAmount(amount: Int)

    fun sendButtonClicked()

    fun cancelButtonClicked()

}