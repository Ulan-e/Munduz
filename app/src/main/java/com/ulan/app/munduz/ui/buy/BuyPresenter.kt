package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BasePresenter

interface BuyPresenter : BasePresenter{

    fun setProducts(purchases: ArrayList<PurchaseEntity>)
    fun setTotal(sum: Int)
    fun setSendEmailHelper(sendEmailHelper: SendEmailHelper)
    fun sendButtonClicked()
    fun cancelButtonClicked()
}