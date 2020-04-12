package com.ulan.app.munduz.ui.catalog

class CatalogPresenterImpl : CatalogPresenter {

    private var mView: CatalogView?

    constructor(mView: CatalogView) {
        this.mView = mView
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun setCatalog(catalog: MutableList<String>) {
        if (catalog.size > 0){
            mView?.showCatalog(catalog)
        }else{
            mView?.showEmptyData()
        }
    }

    override fun detachView() {
        mView = null
    }
}