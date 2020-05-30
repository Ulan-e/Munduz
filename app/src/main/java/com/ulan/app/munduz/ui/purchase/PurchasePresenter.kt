package com.ulan.app.munduz.ui.purchase

import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.helpers.SendEmailHelper

interface PurchasePresenter {

    fun setEmailHelper(emailHelper: SendEmailHelper)

    fun putOrderToMessage(order: Order)

    fun sendViaWhatsApp()

    fun sendViaEmail()

}