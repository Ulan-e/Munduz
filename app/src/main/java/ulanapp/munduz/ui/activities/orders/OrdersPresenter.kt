package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.models.PurchaseEntity


interface OrdersPresenter {

    fun setToolbar()

    fun getAmount() : Int

    fun setProducts(purchases: MutableList<PurchaseEntity>)

    fun setPurchasesAmount(amount: Int)

    fun sendButtonClicked()

    fun cancelButtonClicked()

}