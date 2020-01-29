package com.ulan.app.munduz.ui.home

import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.data.repository.Repository
import javax.inject.Inject

class HomePresenterImpl: HomePresenter{

    private var mRepository: Repository
    private var mView: HomeView? = null

    @Inject
    constructor(view: HomeView, mRepository: Repository) {
        this.mView = view
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {

        mRepository.loadNewProducts(object :
            ProductListCallback {
            override fun onCallback(value: MutableList<Product>) {
                mView?.showProgress()
                mView?.showProducts(value)
                mView?.hideProgress()
            }
        })

    }

    override fun onDetachView() {
        mView = null
    }

}