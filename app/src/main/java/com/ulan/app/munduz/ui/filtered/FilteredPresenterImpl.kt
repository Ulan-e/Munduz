package com.ulan.app.munduz.ui.filtered

import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.ui.home.HomeView
import javax.inject.Inject

class FilteredPresenterImpl: FilteredPresenter {

    private var mView: FilteredView?
    private var mRepository: Repository

    constructor(mView: FilteredView, mRepository: Repository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProductsByCategory(categoryName: String) {
        mView?.showProgress()
        mRepository.loadFilterProducts(categoryName, object : ProductListCallback{
            override fun onCallback(value: MutableList<Product>) {
                mView?.showProducts(value)
            }
        })
        mView?.hideProgress()
    }

    override fun onDetachView() {
        mView = null
    }
}