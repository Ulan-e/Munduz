package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.SendEmailHelper

interface BuyPresenter {

    fun setProduct(product: Product)
    fun setSendEmailHelper(sendEmailHelper: SendEmailHelper)
    fun sendButtonClicked()
    fun cancelButtonClicked()
    fun detachView()
}