package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.models.Order
import ulanapp.munduz.ui.base.BaseView

interface OrdersView : BaseView {

    fun showToolbar()

    fun showTotalPurchases(sum: Int)

    fun getInputOrder(): Order

    fun isNotEmptyFields(): Boolean

    fun cancelOrder()

    fun goToPurchaseMethod(order: Order)

}