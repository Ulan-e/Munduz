package ulanapp.munduz.ui.fragments.catalog

import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class CatalogPresenterImpl @Inject constructor() : BasePresenter<CatalogView>(), CatalogPresenter {

    override fun setCatalog(catalog: MutableList<String>) {
        if (catalog.size > 0) {
            getView()?.showCatalog(catalog)
        } else {
            getView()?.showEmptyData()
        }
    }

}