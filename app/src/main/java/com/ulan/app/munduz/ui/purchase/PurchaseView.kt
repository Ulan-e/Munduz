package com.ulan.app.munduz.ui.purchase

import com.ulan.app.munduz.data.models.Message

interface PurchaseView {

    fun sendViaWhatsAppClicked()
    fun sendViaEmailClicked()
    fun showMessage(message: String)
    fun sendOrderToWhatsApp(message: Message)
    fun closeDialog()

}