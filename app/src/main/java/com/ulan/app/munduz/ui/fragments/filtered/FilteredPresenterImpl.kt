package com.ulan.app.munduz.ui.fragments.filtered

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.interfaces.ProductsCallback
import com.ulan.app.munduz.ui.base.BasePresenter
import javax.inject.Inject

class FilteredPresenterImpl : BasePresenter<FilteredView>, FilteredPresenter {

    private var repository: FirebaseRepository? = null

    @Inject
    constructor(repository: FirebaseRepository) {
        this.repository = repository
    }

    override fun loadProductsByCategory(categoryName: String) {
        repository?.loadProductsByCategory(categoryName, object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                if (values.size > 0) {
                    getView()?.showProducts(values)
                } else {
                    getView()?.showEmptyData()
                }
            }
        })
    }

}