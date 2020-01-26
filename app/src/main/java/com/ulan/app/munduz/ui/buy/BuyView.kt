package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.model.Order

interface BuyView{

    fun getInputOrder() : Order
    fun cancelOrder()
    fun isNotEmptyFields() : Boolean
    fun showSuccessOrder()
}