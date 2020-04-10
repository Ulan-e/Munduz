package com.ulan.app.munduz.ui.catalog

import com.ulan.app.munduz.data.firebase.FirebaseRepository

class CatalogPresenterImpl : CatalogPresenter {

    private var mView: CatalogView?
    private var mRepository: FirebaseRepository

    constructor(mView: CatalogView, mRepository: FirebaseRepository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadCatalog() {
        val categories = mRepository.loadCatalogs()
        if (categories.size > 0){
            mView?.showCatalog(categories)
        }else{
            mView?.showEmptyData()
        }
    }

    override fun detachView() {
        mView = null
    }
}