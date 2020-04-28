package com.ulan.app.munduz.ui.orders

import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.helpers.RUBLE
import javax.inject.Inject

class OrdersPresenterImpl : OrdersPresenter {

    private var mView: OrdersView?
    private lateinit var mPurchases: MutableList<PurchaseEntity>

    @Inject
    constructor(mView: OrdersView) {
        this.mView = mView
    }

    override fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.mPurchases = purchases
    }

    override fun setPurchasesAmount(amount: Int) {
        val goods = "Итого " + mPurchases.size.toString() + " видов товара \n"
        val price = "К оплате " + amount.toString() + RUBLE
        mView?.showTotalPurchases(goods + price)
    }


    override fun sendButtonClicked() {
        if(mView?.isNotEmptyFieldsDelivery()!!){
            mView?.goToPurchaseMethod(mView?.getInputOrder()!!)
        }
    }

    override fun cancelButtonClicked() {
        mView?.cancelOrder()
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun detachView() {
        mView = null
    }
}