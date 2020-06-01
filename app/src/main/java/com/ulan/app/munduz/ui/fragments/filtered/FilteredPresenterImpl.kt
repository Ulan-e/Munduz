package com.ulan.app.munduz.ui.fragments.filtered

import com.ulan.app.munduz.data.repository.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.interfaces.ProductsCallback
import javax.inject.Inject

class FilteredPresenterImpl : FilteredPresenter {

    private var view: FilteredView? = null
    private var repository: FirebaseRepository? = null

    @Inject
    constructor(view: FilteredView, repository: FirebaseRepository) {
        this.view = view
        this.repository = repository
    }

    override fun loadProductsByCategory(categoryName: String) {
        repository?.loadProductsByCategory(categoryName, object : ProductsCallback {
            override fun onCallback(values: MutableList<Product>) {
                if (values.size > 0) {
                    view?.showProducts(values)
                } else {
                    view?.showEmptyData()
                }
            }
        })
    }

    override fun detachView() {
        view = null
        repository = null
    }

}