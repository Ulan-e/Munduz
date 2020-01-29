package com.ulan.app.munduz.ui.catalog

import com.ulan.app.munduz.data.repository.Repository
import javax.inject.Inject

class CatalogPresenterImpl : CatalogPresenter {

    private var mView: CatalogView?
    private var mRepository: Repository

    constructor(mView: CatalogView, mRepository: Repository) {
        this.mView = mView
        this.mRepository = mRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadCatalog() {
        val categories = mRepository.loadCatalogs()
        mView?.showCatalog(categories)
    }

    override fun detachView() {
        mView = null
    }
}