package com.ulan.app.munduz.ui.activities.search

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.interfaces.ProductsCallback
import com.ulan.app.munduz.ui.base.BasePresenter
import javax.inject.Inject

class SearchPresenterImpl : BasePresenter<SearchView>, SearchPresenter {

    private var repository: FirebaseRepository
    private var products = mutableListOf<Product>()

    @Inject
    constructor(repository: FirebaseRepository) {
        this.repository = repository
    }

    override fun setToolbar() {
        getView()?.showToolbar()
    }

    override fun loadProducts() {
        repository.loadProducts(object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                if (values.isNotEmpty()) {
                    getView()?.showProducts(values)
                } else {
                    getView()?.showEmptyData()
                }
            }
        })
    }

}