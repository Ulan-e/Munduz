package com.ulan.app.munduz.ui.buy

import android.text.Html
import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.helpers.convertLongToTime
import javax.inject.Inject

class BuyPresenterImpl : BuyPresenter {

    private var mView: BuyView?
    private var mRepository: FirebaseRepository
    private lateinit var mSendEmailHelper: SendEmailHelper
    private lateinit var mPurchases: ArrayList<PurchaseEntity>

    @Inject
    constructor(mView: BuyView, mRepository: FirebaseRepository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setProducts(purchases: ArrayList<PurchaseEntity>) {
        this.mPurchases = purchases
    }

    override fun setTotal(sum: Int) {
        val rub = Html.fromHtml(" &#x20bd")
        val goods = "Итого " + mPurchases.size.toString() + " Товар"
        val price = "Сумма " + sum.toString() + rub
        mView?.showTotalPurchases(goods + price)
    }

    override fun setSendEmailHelper(sendEmailHelper: SendEmailHelper) {
        mSendEmailHelper = sendEmailHelper
    }

    override fun sendButtonClicked() {
        val order = mView?.getInputOrder()
        if (mView?.isNotEmptyFields() == true) {
            mRepository.insertOrder(order!!)
            sendToEmail(order)
            mView?.showSuccessOrder()
        } else {
            mView?.isNotEmptyFields()
        }
    }

    private fun sendToEmail(order: Order) {
        val email = "uulanerkinbaev@gmail.com"
        val subject = "Приложение Munduz"
        val body =
            ">>> " + order.productName + " количество " + order.productCount + "\n" +
                    ">>> Имя Клиента " + order.clientName + "\n" +
                    ">>> Номер телефона " + order.clientPhoneNumber + "\n" +
                    ">>> С доставкой?" + if (order.withDelivery) "Да" else "Нет"
        val time = System.currentTimeMillis()
        val ordertime = time.convertLongToTime(time)
        mSendEmailHelper.setMessage(email, subject, body, ordertime)
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