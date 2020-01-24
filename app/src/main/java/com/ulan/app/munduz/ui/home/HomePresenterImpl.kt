package com.ulan.app.munduz.ui.home

import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.data.repository.Repository

class HomePresenterImpl: HomePresenter{

    private var mRepository: Repository
    private var mView: HomeView? = null

    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }

    override fun loadProducts() {
        mView?.showProgress()
        mRepository.loadNewProducts(object :
            ProductListCallback {
            override fun onCallback(value: MutableList<Product>) {
                mView?.showProducts(value)
            }
        })
        mView?.hideProgress()
    }

    override fun attachView(view: HomeView) {
        this.mView = view
    }

    override fun onDetachView() {
        mView = null
    }

}