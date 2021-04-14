package ulanapp.munduz.ui.fragments.purchase

import ulanapp.munduz.data.models.Message
import ulanapp.munduz.data.models.Order
import ulanapp.munduz.helpers.SendEmailAsync
import javax.inject.Inject

class PurchasePresenterImpl @Inject constructor(private var view: PurchaseView) :
    PurchasePresenter {

    private lateinit var sendEmailHelper: SendEmailAsync
    private lateinit var message: Message

    override fun setEmailHelper(emailHelper: SendEmailAsync) {
        this.sendEmailHelper = emailHelper
    }

    override fun putOrderToMessage(order: Order) {
        message = Message()
        message.email = "uulanerkinbaev@gmail.com"
        message.subject = "Приложение Munduz"
        message.body =
            order.purchaseMethod + " " + "\n" + "\n" +
                    order.purchases + "\n" +
                    "> Сумма  " + order.amountPurchases + "\n" +
                    "> Покупатель  " + order.clientName + "\n" +
                    "> Телефон  " + order.clientPhoneNumber + "\n" +
                    if (order.comment.isNotEmpty()) {
                        "> Комментарий  " + "*" + order.comment + "\n"
                    } else {
                        ""
                    }
    }

    override fun sendViaWhatsApp() {
        view.sendOrderToWhatsApp(message)
        sendWithEmailHelper()
        view.closeDialog()
    }

    override fun sendViaEmail() {
        sendWithEmailHelper()
        view.showMessage("Ожидайте, вам позвонят в течении 15 минут")
    }

    private fun sendWithEmailHelper() {
        sendEmailHelper.setMessage(message)
        sendEmailHelper.execute()
    }
}