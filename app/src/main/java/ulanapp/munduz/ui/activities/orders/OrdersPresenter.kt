package ulanapp.munduz.ui.activities.orders

interface OrdersPresenter {

    // настрокий тулбара
    fun setToolbar()

    // с доставкой?
    fun isWithDelivery(withDelivery: Boolean)

    // получить сумму
    fun getAmount(): Int

    // получить все заказы
    fun getAllPurchases(): String

    // клик на отправить
    fun sendButtonClicked()

    // отменить заказ
    fun cancelButtonClicked()
}