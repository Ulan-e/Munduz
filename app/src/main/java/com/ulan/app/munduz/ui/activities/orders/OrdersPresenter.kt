package com.ulan.app.munduz.ui.activities.orders

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.ui.base.BasePresenter

interface OrdersPresenter : BasePresenter {

    fun setProducts(purchases: MutableList<PurchaseEntity>)

    fun setPurchasesAmount(amount: Int)

    fun sendButtonClicked()

    fun cancelButtonClicked()

}