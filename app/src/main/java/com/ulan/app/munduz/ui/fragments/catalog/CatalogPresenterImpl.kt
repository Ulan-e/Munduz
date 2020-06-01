package com.ulan.app.munduz.ui.fragments.catalog

class CatalogPresenterImpl : CatalogPresenter {

    private var view: CatalogView?

    constructor(view: CatalogView) {
        this.view = view
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