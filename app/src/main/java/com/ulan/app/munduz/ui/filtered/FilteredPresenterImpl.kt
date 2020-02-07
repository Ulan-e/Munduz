package com.ulan.app.munduz.ui.filtered

import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductListCallback

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
        mRepository.loadFilterProducts(categoryName, object : ProductListCallback {
            override fun onCallback(values: MutableList<Product>) {
                if(values.size > 0){
                    mView?.showProducts(values)
                }else{
                    mView?.showEmptyData()
                }
            }
        })
    }

    override fun detachView() {
        mView = null
    }

}