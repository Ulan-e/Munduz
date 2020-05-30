package com.ulan.app.munduz.ui.orders

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.helpers.RUBLE
import javax.inject.Inject

class OrdersPresenterImpl : OrdersPresenter {

    private var view: OrdersView?
    private lateinit var purchases: MutableList<PurchaseEntity>

    @Inject
    constructor(view: OrdersView) {
        this.view = view
    }

    override fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.purchases = purchases
    }

    override fun setPurchasesAmount(amount: Int) {
        val goods = "Итого " + purchases.size.toString() + " видов товара \n"
        val price = "К оплате " + amount.toString() + RUBLE
        view?.showTotalPurchases(goods + price)
    }


    override fun sendButtonClicked() {
        if(view?.isNotEmptyFieldsDelivery()!!){
            view?.goToPurchaseMethod(view?.getInputOrder()!!)
        }
    }

    override fun cancelButtonClicked() {
        view?.cancelOrder()
    }

    override fun setToolbar() {
        view?.showToolbar()
    }

    override fun detachView() {
        view = null
    }
}