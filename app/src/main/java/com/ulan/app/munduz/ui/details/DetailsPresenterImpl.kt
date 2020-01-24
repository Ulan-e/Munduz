package com.ulan.app.munduz.ui.details

import com.ulan.app.munduz.developer.Product

class DetailsPresenterImpl : DetailsPresenter {

    private var mView: DetailsView?

    constructor(mView: DetailsView) {
        this.mView = mView
    }

    override fun setProduct(product: Product) {
        mView?.showProduct(product)
    }

    override fun setToolbar() {
        mView?.initToolbar("Details")
    }

    override fun buyButtonClicked() {

    }

    override fun favoriteButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        mView?.closeDetails()

    }

    override fun detachView() {
        mView = null
    }

}