package ulanapp.munduz.ui.fragments.catalog

import ulanapp.munduz.ui.base.BaseView

interface CatalogView : BaseView {

    fun showCatalog(catalog: MutableList<String>)
    
}