package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.models.Order
import ulanapp.munduz.ui.base.BaseView

interface OrdersView : BaseView {

    // показать тулбар
    fun showToolbar()

    // показать все заказанные товары
    fun showTotalPurchases(sum: Int)

    // получить текст заказа
    fun getInputOrder(): Order

    // поля не пусты?
    fun isNotEmptyFields(): Boolean

    // отменить заказ
    fun cancelOrder()

    // переход на экран покупки
    fun goToPurchaseMethod(order: Order)
}