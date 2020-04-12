package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.ui.base.BaseView

interface BuyView : BaseView{

    fun showTotalPurchases(total: String)
    fun getInputOrder() : Order
    fun isNotEmptyFields() : Boolean
    fun successOrder()
    fun cancelOrder()
}