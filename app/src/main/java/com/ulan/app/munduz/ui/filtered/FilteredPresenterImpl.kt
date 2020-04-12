package com.ulan.app.munduz.ui.filtered

import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductsCallback

class FilteredPresenterImpl : FilteredPresenter {

    private var mView: FilteredView?
    private var mRepository: FirebaseRepository

    constructor(mView: FilteredView, mRepository: FirebaseRepository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProductsByCategory(categoryName: String) {
            mRepository.loadFilterProducts(categoryName, object : ProductsCallback {
                override fun onCallback(values: MutableList<Product>) {
                    if (values.size > 0) {
                        mView?.showProducts(values)
                    } else {
                        mView?.showEmptyData()
                    }
                }
            })

    }

    override fun detachView() {
        mView = null
    }

}