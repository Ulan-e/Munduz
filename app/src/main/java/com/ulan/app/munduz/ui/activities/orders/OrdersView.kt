package com.ulan.app.munduz.ui.activities.orders

import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.ui.base.BaseView

interface OrdersView : BaseView{

    fun showToolbar()

    fun showTotalPurchases(total: String)

    fun getInputOrder() : Order

    fun isNotEmptyFields() : Boolean

    fun cancelOrder()

    fun goToPurchaseMethod(order: Order)
    
}