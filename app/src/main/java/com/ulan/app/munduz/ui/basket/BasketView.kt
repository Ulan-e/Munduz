package com.ulan.app.munduz.ui.basket

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface BasketView : BaseView {

    fun showProducts(purchases : MutableList<PurchaseEntity>)
    fun purchaseAll(purchases : MutableList<PurchaseEntity>, amount: Int)
    fun showPurchasesAmount(amount: Int)
    fun showGoToHome()
    fun hidePurchaseButton()
    fun showPurchaseButton()

}