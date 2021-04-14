package ulanapp.munduz.ui.fragments.catalog

import ulanapp.munduz.ui.base.BaseView

interface CatalogView : BaseView {

    // показать каталог
    fun showCatalog(catalog: MutableList<String>)
}