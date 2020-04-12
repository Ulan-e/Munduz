package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductsCallback

class SearchPresenterImpl: SearchPresenter{

    private var mView: SearchView?
    private var mRepository: FirebaseRepository
    private var mProducts = mutableListOf<Product>()

    constructor(mView: SearchView, mRepository: FirebaseRepository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts(): MutableList<Product> {
        mRepository.loadSearchedProducts(object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                mView?.showProducts(values)
                mProducts = values
            }

        })
        return mProducts
    }

    override fun detachView() {
        this.mView = null
    }

}