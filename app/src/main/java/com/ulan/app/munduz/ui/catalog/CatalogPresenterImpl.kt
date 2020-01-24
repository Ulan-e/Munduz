package com.ulan.app.munduz.ui.catalog

import com.ulan.app.munduz.data.repository.Repository

class CatalogPresenterImpl : CatalogPresenter {

    private var mView: CatalogView?
    private var mRepository: Repository

    constructor(mView: CatalogView, mRepository: Repository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun loadCatalog() {
        val categories = mRepository.loadCatalogs()
        mView?.showCatalog(categories)
    }

    override fun detachView() {
        mView = null
    }
}