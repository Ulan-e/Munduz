package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.ui.base.BaseView

interface BuyView : BaseView{

    fun getInputOrder() : Order
    fun isNotEmptyFields() : Boolean
    fun showSuccessOrder()
    fun cancelOrder()
}