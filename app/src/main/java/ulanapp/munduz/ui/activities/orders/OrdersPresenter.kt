package ulanapp.munduz.ui.activities.orders

import ulanapp.munduz.data.room.repository.PurchasesRepository


interface OrdersPresenter {

    fun setToolbar()

    fun setWithDeliveryOrNot(withDelivery: Boolean)

    fun getAmount(): Int
/*
    fun setRepository(purchasesRepository: PurchasesRepository)*/

    fun getAllPurchases(): String

    fun sendButtonClicked()

    fun cancelButtonClicked()

}