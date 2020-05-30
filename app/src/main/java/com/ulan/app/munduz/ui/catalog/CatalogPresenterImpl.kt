package com.ulan.app.munduz.ui.catalog

class CatalogPresenterImpl : CatalogPresenter {

    private var view: CatalogView?

    constructor(view: CatalogView) {
        this.view = view
    }

    override fun setToolbar() {
        view?.showToolbar()
    }

    override fun setCatalog(catalog: MutableList<String>) {
        if (catalog.size > 0) {
            view?.showCatalog(catalog)
        } else {
            view?.showEmptyData()
        }
    }

    override fun detachView() {
        view = null
    }
}