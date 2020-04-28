package com.ulan.app.munduz.ui.purchase

import com.ulan.app.munduz.data.models.Message
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.helpers.SendEmailHelper
import javax.inject.Inject

class PurchasePresenterImpl : PurchasePresenter {

    private var mView: PurchaseView
    private lateinit var mSendEmailHelper: SendEmailHelper
    private lateinit var mMessage: Message

    @Inject
    constructor(view: PurchaseView) {
        this.mView = view
    }

    override fun setEmailHelper(emailHelper: SendEmailHelper) {
        this.mSendEmailHelper = emailHelper
    }

    override fun putOrderToMessage(order: Order) {
        mMessage = Message()
        mMessage.email = "uulanerkinbaev@gmail.com"
        mMessage.subject = "Приложение Munduz"
        mMessage.body =
            "               Товары " + "\n" +
                    order.purchases + "\n" +
                    "> Сумма заказа  " + order.amountPurchases + "\n" +
                    "> Имя Клиента  " + order.clientName + "\n" +
                    "> Номер телефона  " + order.clientPhoneNumber + "\n" +
                    "> Способ покупки  " + order.purchaseMethod + "\n" +
                    "> Комментарий  " + order.comment + "\n"
    }

    override fun sendViaWhatsApp() {
        mView.sendOrderToWhatsApp(mMessage)
        sendWithEmailHelper()
        mView?.closeDialog()
    }

    override fun sendViaEmail() {
        sendWithEmailHelper()
        mView.showMessage("Ожидайте, вам позвонят в течении 15 минут")
    }

    private fun sendWithEmailHelper() {
        mSendEmailHelper.setMessage(mMessage)
        mSendEmailHelper.execute()
    }

}