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

    override fun loadProducts(): MutableList<Product> {
        repository.loadSearchedProducts(object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                view?.showProducts(values)
                products = values
            }

        })
        return products
    }

    override fun detachView() {
        this.view = null
    }

}