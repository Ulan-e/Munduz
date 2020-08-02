package ulanapp.munduz.ui.fragments.purchase

import ulanapp.munduz.data.models.Order
import ulanapp.munduz.helpers.SendEmailAsync

interface PurchasePresenter {

    fun setEmailHelper(emailHelper: SendEmailAsync)

    fun putOrderToMessage(order: Order)

    fun sendViaWhatsApp()

    fun sendViaEmail()

}