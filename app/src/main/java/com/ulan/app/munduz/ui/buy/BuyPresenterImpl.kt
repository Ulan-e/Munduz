package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.helpers.convertLongToTime
import kotlinx.android.synthetic.main.buy_layout.*
import javax.inject.Inject

class BuyPresenterImpl : BuyPresenter {

    private var mView: BuyView?
    private var mRepository: Repository
    private lateinit var mSendEmailHelper: SendEmailHelper
    private lateinit var mProduct: Product

    @Inject
    constructor(mView: BuyView, mRepository: Repository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setProduct(product: Product) {
        this.mProduct = product
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
                ">>> С доставкой?" + if(order.withDelivery) "Да" else "Нет"
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