package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductsCallback

class SearchPresenterImpl: SearchPresenter{

    private var view: SearchView?
    private var repository: FirebaseRepository
    private var products = mutableListOf<Product>()

    constructor(view: SearchView, repository: FirebaseRepository) {
        this.view = view
        this.repository = repository
    }

    override fun setToolbar() {
        view?.showToolbar()
    }

    override fun loadProducts() {
        repository.loadProducts(object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                if (values.isNotEmpty()) {
                    view?.showProducts(values)
                } else {
                    view?.showEmptyData()
                }
            }
        })
    }

    override fun detachView() {
        this.view = null
    }

}