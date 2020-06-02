package ulanapp.munduz.ui.fragments.purchase

import ulanapp.munduz.data.models.Message


interface PurchaseView {

    fun sendViaWhatsAppClicked()

    fun sendViaEmailClicked()

    fun showMessage(message: String)

    fun sendOrderToWhatsApp(message: Message)

    fun closeDialog()

}