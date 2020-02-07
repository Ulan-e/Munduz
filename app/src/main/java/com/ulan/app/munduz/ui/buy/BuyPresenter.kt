package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BasePresenter

interface BuyPresenter : BasePresenter{

    fun setProduct(product: Product)
    fun setSendEmailHelper(sendEmailHelper: SendEmailHelper)
    fun sendButtonClicked()
    fun cancelButtonClicked()
}