package com.ulan.app.munduz.ui.basket

import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface BasketView : BaseView {

    fun showAllProducts(purchases : ArrayList<PurchaseEntity>)
    fun purchaseAll(purchases : ArrayList<PurchaseEntity>, price: Int)
    fun showSumOfPurchases(sum: Int)
    fun hidePurchaseButton()
    fun showPurchaseButton()

}