package ulanapp.munduz.ui.fragments.purchase

import ulanapp.munduz.data.models.Order
import ulanapp.munduz.helpers.SendEmailHelper

interface PurchasePresenter {

    fun setEmailHelper(emailHelper: SendEmailHelper)

    fun putOrderToMessage(order: Order)

    fun sendViaWhatsApp()

    fun sendViaEmail()

}