package com.ulan.app.munduz.ui.catalog

import com.ulan.app.munduz.ui.base.BaseView

interface CatalogView : BaseView {

    fun showCatalog(catalog: MutableList<String>)
    
}