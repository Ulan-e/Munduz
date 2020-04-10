package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface BuyView : BaseView{

    fun showTotalPurchases(total: String)
    fun getInputOrder() : Order
    fun isNotEmptyFields() : Boolean
    fun showSuccessOrder()
    fun cancelOrder()
}