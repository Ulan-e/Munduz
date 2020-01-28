package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductsCallback

class SearchPresenterImpl: SearchPresenter{

    private var mView: SearchView?
    private var mRepository: Repository
    private var mProducts = ArrayList<Product>()

    constructor(mView: SearchView, mRepository: Repository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setProducts(products: ArrayList<Product>) {
        this.mProducts = products
    }


    override fun detachView() {
        this.mView = null
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

}