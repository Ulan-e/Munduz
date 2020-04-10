package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductsCallback

class SearchPresenterImpl: SearchPresenter{

    private var mView: SearchView?
    private var mRepository: FirebaseRepository
    private var mProducts = ArrayList<Product>()

    constructor(mView: SearchView, mRepository: FirebaseRepository) {
        this.mView = mView
        this.mRepository = mRepository
    }


    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts(): ArrayList<Product> {
        mRepository.loadSearchedProducts(object : ProductsCallback {
            override fun onCallback(products: ArrayList<Product>) {
                mView?.showProducts(products)
                mProducts = products
            }

        })
        return mProducts
    }

    override fun detachView() {
        this.mView = null
    }

}