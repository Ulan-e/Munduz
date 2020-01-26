package com.ulan.app.munduz.ui.buy

import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.developer.Product

class BuyPresenterImpl : BuyPresenter {

    private var mView: BuyView?
    private var mRepository: Repository
    private lateinit var mProduct: Product

    constructor(mView: BuyView, mRepository: Repository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setProduct(product: Product) {
        this.mProduct = product
    }

    override fun sendButtonClicked() {
        val order = mView?.getInputOrder()
        mRepository.insertOrder(order!!)
        if(mView?.isNotEmptyFields() == true) {
            mView?.showSuccessOrder()
        }else{
            mView?.isNotEmptyFields()
        }
    }

    override fun cancelButtonClicked() {
       mView?.cancelOrder()
    }

    override fun detachView() {
        mView = null
    }
}