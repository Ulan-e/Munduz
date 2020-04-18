package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.helpers.SendEmailHelper
import javax.inject.Inject

class BuyPresenterImpl : BuyPresenter {

    private var mView: BuyView?
    private var mRepository: FirebaseRepository
    private lateinit var mSendEmailHelper: SendEmailHelper
    private lateinit var mPurchases: MutableList<PurchaseEntity>

    @Inject
    constructor(mView: BuyView, mRepository: FirebaseRepository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.mPurchases = purchases
    }

    override fun setPurchasesAmount(amount: Int) {
        val goods = "Итого " + mPurchases.size.toString() + " видов товара \n"
        val price = "К оплате " + amount.toString() + RUBLE
        mView?.showTotalPurchases(goods + price)
    }

    override fun setSendEmailHelper(sendEmailHelper: SendEmailHelper) {
        mSendEmailHelper = sendEmailHelper
    }

    override fun sendButtonClicked() {
        val order = mView?.getInputOrder()
        if (mView?.isNotEmptyFields() == true) {
            sendToEmail(order!!)
            mView?.successOrder()
        } else {
            mView?.isNotEmptyFields()
        }
    }



    private fun sendToEmail(order: Order) {
        val email = "uulanerkinbaev@gmail.com"
        val subject = "Приложение Munduz"
        val body =
            order.purchases + "\n"+
                    "> Сумма заказа  " + order.amountPurchases + "\n" +
                    "> Имя Клиента  " + order.clientName + "\n" +
                    "> Номер телефона  " + order.clientPhoneNumber + "\n" +
                    "> Номер телефона 2 " + order.clientPhoneNumberSecond + "\n" +
                    "> Способ покупки  " + order.purchaseMethod + "\n" +
                    "> Комментарий  " + order.comment + "\n"

        mSendEmailHelper.setMessage(email, subject, body)
        mSendEmailHelper.execute()

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